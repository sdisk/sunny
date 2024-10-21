package com.xiaoyu.sunny.service.user.service;

import com.xiaoyu.sunny.service.user.dto.IssuanceCouponDTO;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 17:46
 */
public interface CouponService {
    /**
     * 建券
     */
    void createCoupon();

    /**
     * 发券
     * @param issuanceCouponDTO
     */
    void issuanceCoupon(IssuanceCouponDTO issuanceCouponDTO);


    /**
     * 领券
     * batchId: 优惠券批次id
     * stepNumber: 固定步长
     */
    void receiveCoupon(Long batchId, int stepNumber);

    /**
     * 用券
     * couponId: 优惠券id
     * orderId: 订单id
     */
    void useCoupon(Long couponId, Long orderId);
}
