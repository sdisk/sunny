package com.xiaoyu.sunny.service.user.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import com.xiaoyu.sunny.dao.coupon.entity.CouponBatchPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponInfoPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponRulePO;
import com.xiaoyu.sunny.service.cache.RedisOperator;
import com.xiaoyu.sunny.common.constants.CacheConstant;
import com.xiaoyu.sunny.service.enums.CouponStatusEnum;
import com.xiaoyu.sunny.service.user.dto.IssuanceCouponDTO;
import com.xiaoyu.sunny.service.user.dto.IssuanceCouponMessage;
import com.xiaoyu.sunny.service.user.dto.UserDTO;
import com.xiaoyu.sunny.service.user.service.CouponService;
import com.xiaoyu.sunny.service.util.RocketMqHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 17:47
 */
@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private RedisOperator redisOperator;

    @Resource
    private RocketMqHelper rocketMqHelper;

    @Override
    public void createCoupon() {
        //1.建立券模板
        createCouponTemplate();

        //2.建立券规则
        createCouponRule();

        //3.建立券批次
        createCouponBatch();

    }

    @Override
    public void issuanceCoupon(IssuanceCouponDTO issuanceCouponDTO) {
        //1.获取用户的excel数据
        List<Long> userIdList = getExcelDataFromUrl(issuanceCouponDTO.getExcelUrl());
        Long couponBatchId = issuanceCouponDTO.getCouponBatchId();

        //2.构建发券message, 发送消息
        List<IssuanceCouponMessage> messageList = userIdList.stream()
                .map(x -> {
                    IssuanceCouponMessage issuanceCouponMessage = new IssuanceCouponMessage();
                    issuanceCouponMessage.setIssuanceCouponId(couponBatchId);
                    issuanceCouponMessage.setUserId(x);
                    return issuanceCouponMessage;
                }).collect(Collectors.toList());

        messageList.forEach(message->{
            rocketMqHelper.syncSendOrderly("ISSUANCE_PRODUCER", MessageBuilder.withPayload(message).build(), String.valueOf(message.getUserId()));
        });

    }

    @Override
    public void receiveCoupon(Long batchId, int stepNumber) {
        //校验是否已经领取
        String key = "coupon:receive:" + getUserId();
        if(redisOperator.getSetOperator().isMember(key, batchId)){
            log.info("该用户已经领取该批次的优惠券");
            return;
        }

        //校验优惠券库存余量

        //采用redis+db库存扣减方案
        //1、首次发券时从数据库申请固定步长库存，我们称为增加预扣库存，然后将预扣库存放入redis中。
        //2、后续每次请求都从redis扣减，直到扣完。由于在这个过程中存在并发，可能多扣，所以我们需要将多扣的库存进行归还。
        //3、扣完后再次增加预扣库存，同样再次申请时也会存在并发，所以我们增加了双重检查锁，来确保增加数据库和redis的量是原子的。
        int receiveCouponNumber = 1;
        int stockNumber = getStockFromCache();
        if (stockNumber < receiveCouponNumber){
            //redis库存不足, 从db中进行申请
            int dbStockNumber = getStockFromDB(stepNumber);
            redisOperator.getValueOperator().set("", String.valueOf(dbStockNumber));
        }
        redisOperator.getValueOperator().decrement("", 1);
        int newStockFromCache = getStockFromCache();
        if (newStockFromCache < stockNumber-1){
            //存在并发，多扣减了redis库存,要进行归还
            redisOperator.getValueOperator().set("", String.valueOf(stockNumber-1));
        }

        //使用lua脚本进行优化，把get，incr的命令都放在一起进行执行
        /**
         * -- 库存未预热
         * if (redis.call('exists', KEYS[2]) == 1) then
         *     return -9;
         * end;
         * -- 秒杀商品库存存在
         * if (redis.call('exists', KEYS[1]) == 1) then
         *     local stock = tonumber(redis.call('get', KEYS[1]));
         *     local num = tonumber(ARGV[1]);
         *     -- 剩余库存少于请求数量
         *     if (stock < num) then
         *         return -3
         *     end;
         *     -- 扣减库存
         *     if (stock >= num) then
         *         redis.call('incrby', KEYS[1], 0 - num);
         *         -- 扣减成功
         *         return 1
         *     end;
         *     return -2;
         * end;
         * -- 秒杀商品库存不存在
         * return -1;
         */
        redisOperator.getSetOperator().add(key, batchId);
        //插入券信息表
        CouponInfoPO couponInfoPO = buildCouponInfo(getUserId(), batchId);
        //保存数据
        //saveCouponInfoPO(couponInfoPO);
    }

    @Override
    public void useCoupon(Long couponId, Long orderId) {
        //选择优惠券进行下单, 状态为已锁定
        CouponInfoPO couponInfoPO = getByCouponId();
        couponInfoPO.setCouponStatus(CouponStatusEnum.LOCK.getCode());
        couponInfoPO.setOrderId(orderId);
        saveCouponInfo(couponInfoPO);
    }

    private void saveCouponInfo(CouponInfoPO couponInfoPO) {
    }

    private CouponInfoPO getByCouponId() {
        return null;
    }

    private CouponInfoPO buildCouponInfo(Long userId, Long batchId) {
        CouponInfoPO couponInfoPO = new CouponInfoPO();
        couponInfoPO.setBatchId(batchId);
        couponInfoPO.setUserId(userId);
        couponInfoPO.setReceiveTime(new Date());

        Snowflake snowflake = IdUtil.createSnowflake(1,1);
        couponInfoPO.setCouponId(snowflake.nextId());

//        Long ruleId = couponBatchPO.getRuleId();
//        CouponRulePO couponRulePO = ueryCouponRuleByRuleId(ruleId);
//        String ruleContent = couponRulePO.getRuleContent();
//
//
//        Date validateTime = getValidateTimeOfRuleContent(ruleContent);
//        couponInfoPO.setValidateTime(validateTime);

        return couponInfoPO;
    }

    Long getUserId(){
        ThreadLocal<UserDTO> local = new ThreadLocal<>();
        UserDTO userDTO = local.get();
        return userDTO.getId();
    }


    private int getStockFromDB(int stepNumber) {
        //select total_count, assign_count from coupon_batch where id=xx;
        CouponBatchPO couponBatchPo = new CouponBatchPO();
        if (couponBatchPo.getTotalCount()-couponBatchPo.getAssignCount() > stepNumber){
            //update coupon_batch set assign_count = assign_count + stepNumber where id=xx;
            return stepNumber;
        } else {
            int remainCount = couponBatchPo.getTotalCount() - couponBatchPo.getAssignCount();
            //update coupon_batch set assign_count = assign_count +remainCount  where id=xx;
            return remainCount;
        }
    }

    private int getStockFromCache() {
        String stockNumber = redisOperator.getValueOperator().get("");
        return Integer.parseInt(stockNumber);
    }

    private List<Long> getExcelDataFromUrl(String excelUrl) {
        //1.下载文件
        //2.解析数据，获取 List<Long> userIdList
        return Lists.newArrayList();
    }


    /**
     * 优惠券系统 -> 券模板管理
     * 运营人员登录系统到这个页面
     *  template_code\template_name\available\start_time\end_time\type
     * insert into coupon_template (template_code, template_name, available, start_time, end_time, type, create_user,update_user)
     * values("001", "满减券", 1, "2024-01-01 00:00:00", "2024-12-31 23:59:59", 1,'system' ,'system');
     */
    private void createCouponTemplate() {

    }

    /**
     * insert into coupon_rule(name, type, rule_content)
     * values ("满减规则", 0, "{
     *   threshold: 10000 // 使用门槛
     *   amount: 500 // 优惠金额
     *   use_range: 3 // 使用范围，0—全场，1—商家，2—类目，3—商品
     *   commodity_id: 10 // 商品 id
     *   receive_count: 1 // 每个用户可以领取的数量
     *   is_mutex: true // 是否互斥，true 表示互斥，false 表示不互斥
     *   receive_started_at: 2024-11-01 00:08:00 // 领取开始时间
     *   receive_ended_at: 2024-11-06 00:08:00 // 领取结束时间
     *   use_started_at: 2024-11-01 00:00:00 // 使用开始时间
     *   use_ended_at: 2024-11-11 11:59:59 // 使用结束时间
     * }")
     */
    private void createCouponRule() {

    }

    /**
     * insert into coupon_batch (batch_id,batch_name,coupon_name, template_id, rule_id,total_count,assign_count, used_count,create_user,update_user)
     * values (1, "双11满减1批次", "iphone16商品满减券", 1, 1, 10000, 0, 0,'system' ,'system');
     */
    private void createCouponBatch() {
        //1.根据券模板和券规则数据生成券批次数据

        //2.结合运营给的 券的总库存（10000）保存一条券批次数据到DB

        //3.保存到缓存-redis(预热)

        //3.1 拆key
        int totalCount = 10000;
        int singleCount = 50;

        int batchId = 1;

        int count = totalCount/singleCount;

        String couponBatchStockKey = CacheConstant.COUPON_BATCH_STOCK_CATEGORY + CacheConstant.COUPON_BATCH_SEG + batchId;

        IntStream.rangeClosed(1, count).forEach(i -> {
            redisOperator.getValueOperator().set(couponBatchStockKey+CacheConstant.COUPON_BATCH_SEG+i, String.valueOf(singleCount));
        });

        /**
         * 分片数量 = 总库存/单个分片的库存数量
         * 若取单个分片的库存数量为50，10000/50 = 200。
         * redis key设计：coupon_batch_stock_批次id_分片id
         * coupon_batch_stock_1_1
         * coupon_batch_stock_1_2
         * ...
         * coupon_batch_stock_1_200
         */

    }



}
