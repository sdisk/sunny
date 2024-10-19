package com.xiaoyu.sunny.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 18:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceCouponDTO {
    /**
     * 券批次id
     */
    private Long couponBatchId;
    /**
     * 上传excel url
     */
    private String excelUrl;
}
