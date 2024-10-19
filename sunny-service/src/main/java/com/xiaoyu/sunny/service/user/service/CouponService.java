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
}
