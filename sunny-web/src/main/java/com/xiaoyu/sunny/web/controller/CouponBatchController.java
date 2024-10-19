package com.xiaoyu.sunny.web.controller;

import com.xiaoyu.sunny.service.user.service.CouponService;
import com.xiaoyu.sunny.web.base.BaseResultVO;
import com.xiaoyu.sunny.web.req.IssuanceCouponRequest;
import com.xiaoyu.sunny.web.util.ResultsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/19 17:54
 */
@Api(tags = "券批次管理", value = "券批次管理")
@RestController
@RequestMapping("/coupon/batch")
public class CouponBatchController {

    @Resource
    private CouponService couponService;
    //新增
    //删除
    //更新
    //页面查询
    //发券接口
    @ApiOperation(value="发券", notes="发券")
    @GetMapping("/issuance/")
    public BaseResultVO<Void> issuanceCoupon(@RequestBody IssuanceCouponRequest issuanceCouponRequest){
        couponService.issuanceCoupon(IssuanceCouponRequest.toIssuanceCouponDTO(issuanceCouponRequest));
        return ResultsUtil.successed();
    }
}
