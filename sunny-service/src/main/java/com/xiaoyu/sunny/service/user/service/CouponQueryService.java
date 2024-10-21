package com.xiaoyu.sunny.service.user.service;

import com.xiaoyu.sunny.dao.coupon.entity.CouponBatchPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponInfoPO;
import com.xiaoyu.sunny.dao.coupon.entity.CouponRulePO;
import com.xiaoyu.sunny.service.user.dto.CouponInfoDTO;

import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 19:16
 */
public interface CouponQueryService {
    CouponBatchPO queryByCouponBatchId(Long issuanceCouponId);

    CouponRulePO queryCouponRuleByRuleId(Long ruleId);

    /**
     * 查询可使用的优惠券
     * userId： 用户id
     * commodityId: 商品id
     * @return
     */
    List<CouponInfoDTO> queryCouponInfoByUserIdAndCommodityId(Long userId, String commodityId);
}
