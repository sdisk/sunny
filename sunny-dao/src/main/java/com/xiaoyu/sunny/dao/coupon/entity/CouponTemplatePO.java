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
@Table(name = "coupon_template")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponTemplatePO {

    private Long id;
    private String templateCode;
    private String templateName;
    private Integer available;
    private Date startTime;
    private Date endTime;
    private Integer type;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;
}
