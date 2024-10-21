package com.xiaoyu.sunny.web.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 21:00
 */
@Data
public class OrderDTO {

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 使用的优惠券列表
     */
    private List<Long> couponIdList;
    /**
     * 支付金额
     */
    private Integer payAmount;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 是否支付成功：true：支付成功 false 支付失败、超时取消
     */
    private boolean isSuccess;
}
