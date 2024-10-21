package com.xiaoyu.sunny.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 20:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponRuleDTO {

    private Long id;
    private String name;
    private Integer type;

    private Integer threshold;
    private Integer amount;
    private Integer useRange;
    private String commodityId;
    private Integer receiveCount;
    private boolean isMutex;
    private Date receiveStartedAt;
    private Date receiveEndedAt;
    private Date useStartedAt;
    private Date useEndedAt;
}
