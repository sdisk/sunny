package com.xiaoyu.sunny.service.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 18:30
 */
@Data
public class IssuanceCouponMessage implements Serializable {
    private static final long serialVersionUID = 735352786024616467L;

    private Long issuanceCouponId;
    private Long userId;
}
