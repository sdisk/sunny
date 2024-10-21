package com.xiaoyu.sunny.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 20:52
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum CouponStatusEnum {
    INIT(0, "新创建"),
    RECEIVE(1, "已领取"),
    LOCK(2, "已锁定"),
    USED(3, "已核销"),
    ;

    private int code;
    private String desc;

    public static CouponStatusEnum getByCode(int code) {
        for (CouponStatusEnum couponStatusEnum : CouponStatusEnum.values()) {
            if (couponStatusEnum.getCode() == code) {
                return couponStatusEnum;
            }
        }
        return null;
    }
}
