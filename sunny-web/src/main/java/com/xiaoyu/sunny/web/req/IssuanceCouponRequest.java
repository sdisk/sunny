package com.xiaoyu.sunny.web.req;

import com.xiaoyu.sunny.service.user.dto.IssuanceCouponDTO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 18:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("发券请求")
public class IssuanceCouponRequest {
    /**
     * 券批次id
     */
    private Long couponBatchId;
    /**
     * 上传excel url
     */
    private String excelUrl;

    public static IssuanceCouponDTO toIssuanceCouponDTO(IssuanceCouponRequest request){
        return IssuanceCouponDTO.builder()
                .couponBatchId(request.getCouponBatchId())
                .excelUrl(request.getExcelUrl())
                .build();
    }
}
