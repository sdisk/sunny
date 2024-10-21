package com.xiaoyu.sunny.dao.coupon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 17:38
 */
@Table(name = "coupon_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponInfoPO {

    private Long id;
    private Long couponId;
    private Long userId;
    private Long batchId;
    private Long orderId;
    private Date receiveTime;
    private Date validateTime;
    private Date usedTime;
    private Integer couponStatus;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;
}
