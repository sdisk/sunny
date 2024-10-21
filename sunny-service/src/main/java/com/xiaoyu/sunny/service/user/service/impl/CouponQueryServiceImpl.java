package com.xiaoyu.sunny.service.user.service.impl;

import com.xiaoyu.sunny.dao.coupon.entity.CouponBatchPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponInfoPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponRulePO;
import com.xiaoyu.sunny.dao.coupon.mapper.CouponBatchMapper;
import com.xiaoyu.sunny.dao.coupon.mapper.CouponInfoMapper;
import com.xiaoyu.sunny.dao.coupon.mapper.CouponRuleMapper;
import com.xiaoyu.sunny.dao.coupon.mapper.CouponTemplateMapper;
import com.xiaoyu.sunny.service.enums.CouponStatusEnum;
import com.xiaoyu.sunny.service.user.dto.CouponInfoDTO;
import com.xiaoyu.sunny.service.user.service.CouponQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 19:16
 */
@Slf4j
@Service
public class CouponQueryServiceImpl implements CouponQueryService {


    @Resource
    private CouponTemplateMapper couponTemplateMapper;

    @Resource
    private CouponRuleMapper couponRuleMapper;

    @Resource
    private CouponBatchMapper couponBatchMapper;

    @Resource
    private CouponInfoMapper couponInfoMapper;


    @Override
    public CouponBatchPO queryByCouponBatchId(Long issuanceCouponId) {
        return couponBatchMapper.selectByPrimaryKey(issuanceCouponId);
    }

    @Override
    public CouponRulePO queryCouponRuleByRuleId(Long ruleId) {
        return couponRuleMapper.selectByPrimaryKey(ruleId);
    }

    @Override
    public  List<CouponInfoDTO> queryCouponInfoByUserIdAndCommodityId(Long userId, String commodityId){

        /**
         * SELECT batch_id FROM coupon_info WHERE user_id = 1001 AND status = 0;
         * SELECT rule_id FROM coupon_batch WHERE batch_id in (1,2,3);
         * SELECT name, type, rule_content FROM rule WHERE rule_id in (1,2,3);
         */
        CouponInfoPO couponInfoPO = new CouponInfoPO();
        couponInfoPO.setUserId(userId);
        couponInfoPO.setCouponStatus(CouponStatusEnum.RECEIVE.getCode());
        List<CouponInfoPO> couponInfoPOList = couponInfoMapper.selectByCondition(couponInfoPO);

        List<Long> couponBatchIds = couponInfoPOList.stream()
                .map(CouponInfoPO::getBatchId)
                .collect(Collectors.toList());

        List<CouponBatchPO> couponBatchPOList = couponBatchMapper.selectByIds(getIdString(couponBatchIds));

        List<Long> rules = couponBatchPOList.stream()
                .map(CouponBatchPO::getRuleId)
                .collect(Collectors.toList());

        String ids = getIdString(rules);
        List<CouponRulePO> couponRulePOList = couponRuleMapper.selectByIds(ids);

        couponRulePOList.forEach((couponRulePO)->{
            String ruleContent = couponRulePO.getRuleContent();
            //校验，筛选数据，进行返回
        });
        //根据查询出来的券规则进行校验： 1 商品id/类目校验， 可用的时间进行校验
        return Collections.emptyList();
    }

    private String getIdString(List<Long> rules) {
        StringBuilder builder = new StringBuilder();
        for (Long ruleId : rules) {
            if (builder.length() > 0) {
                builder.append(ruleId).append(",");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
