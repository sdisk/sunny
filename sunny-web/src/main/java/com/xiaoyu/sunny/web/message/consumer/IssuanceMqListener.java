package com.xiaoyu.sunny.web.message.consumer;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.xiaoyu.sunny.common.constants.CacheConstant;
import com.xiaoyu.sunny.dao.coupon.entity.CouponBatchPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponInfoPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponRulePO;
import com.xiaoyu.sunny.service.cache.RedisOperator;
import com.xiaoyu.sunny.service.user.dto.IssuanceCouponMessage;
import com.xiaoyu.sunny.service.user.service.CouponQueryService;
import com.xiaoyu.sunny.web.domain.PushMessageDTO;
import com.xiaoyu.sunny.web.util.RandomNumberGenerator;
import com.xiaoyu.sunny.web.util.RocketMqHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:
 * @Author XiaoYu
 * @Date 2024/9/15 20:06
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "ISSUANCE_PRODUCER")
public class IssuanceMqListener implements RocketMQListener<IssuanceCouponMessage> {

    @Resource
    private CouponQueryService couponQueryService;

    @Resource
    private RocketMqHelper rocketMqHelper;

    @Resource
    private RedisOperator redisOperator;


    @Override
    public void onMessage(IssuanceCouponMessage issuanceCouponMessage) {
        log.info("接收到消息，开始消费..message={}", JSONUtil.toJsonStr(issuanceCouponMessage));
        //1.根据券批次id查询券批次的相关信息
        Long issuanceCouponId = issuanceCouponMessage.getIssuanceCouponId();
        CouponBatchPO couponBatchPO = couponQueryService.queryByCouponBatchId(issuanceCouponId);

        //2.构建优惠券信息表数据，保存到db
        CouponInfoPO couponInfoPO = buildCouponInfo(couponBatchPO, issuanceCouponMessage.getUserId());
        //保存数据
        //saveCouponInfoPO(couponInfoPO);
        //更新库存
        updateStock(couponBatchPO);

        //3.发送mq ->（下游触发平台监听消息，push到用户侧）
        PushMessageDTO messageDTO = buildPushMessageDTO();
        rocketMqHelper.asyncSend("PUSH_MESSAGE",
                MessageBuilder.withPayload(messageDTO).build());
        log.info("----------");
    }

    private void updateStock(CouponBatchPO couponBatchPO) {
        //先更新db还是先更新redis,缓存db数据一致性问题？-> 先更新db再更新redis

        //更新db库存数据
        Integer totalCount = couponBatchPO.getTotalCount();
        totalCount--;
        couponBatchPO.setTotalCount(totalCount);
        //乐观锁 -> version字段
        //update coupon_batch set totalCount=totalCount-1 , version=version+1 where id=xx and version=?
        //悲观锁
        //select * from coupon_batch where id=xx for update;
        //update coupon_batch set totalCount=totalCount-1 where id=xx;

        //更新redis库存数据
        int randomNumber = RandomNumberGenerator.getRandomNumber();
        String couponBatchStockKey = CacheConstant.COUPON_BATCH_STOCK_CATEGORY + CacheConstant.COUPON_BATCH_SEG + randomNumber;
        redisOperator.getValueOperator().decrement(couponBatchStockKey, 1);
    }

    private PushMessageDTO buildPushMessageDTO() {
        PushMessageDTO pushMessageDTO = new PushMessageDTO();
        //构建消息体
        String message = "";
        //[xx公司] 亲爱的用户你好，由于你是优质客户，特意发放一张[xx]券，请查收。
        // message_template -> [{companyName}公司] 亲爱的用户你好，由于你是优质客户，特意发放一张{couponName}券，请查收。

        return pushMessageDTO;
    }

    private CouponInfoPO buildCouponInfo(CouponBatchPO couponBatchPO, Long userId) {
        CouponInfoPO couponInfoPO = new CouponInfoPO();
        couponInfoPO.setBatchId(couponBatchPO.getId());
        couponInfoPO.setUserId(userId);
        couponInfoPO.setReceiveTime(new Date());

        Snowflake snowflake = IdUtil.createSnowflake(1,1);
        couponInfoPO.setCouponId(snowflake.nextId());

        Long ruleId = couponBatchPO.getRuleId();
        CouponRulePO couponRulePO = couponQueryService.queryCouponRuleByRuleId(ruleId);
        String ruleContent = couponRulePO.getRuleContent();


        Date validateTime = getValidateTimeOfRuleContent(ruleContent);
        couponInfoPO.setValidateTime(validateTime);

        return couponInfoPO;
    }

    private Date getValidateTimeOfRuleContent(String ruleContent) {
        //解析json成对象，取对应属性
        return null;
    }
}
