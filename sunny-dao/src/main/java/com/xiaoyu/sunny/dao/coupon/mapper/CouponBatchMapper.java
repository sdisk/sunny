package com.xiaoyu.sunny.dao.coupon.mapper;

import com.xiaoyu.sunny.dao.base.Mapper;
import com.xiaoyu.sunny.dao.coupon.entity.CouponBatchPO;
import com.xiaoyu.sunny.dao.user.entity.UserPO;

import java.util.List;

/**
 * @Description: UserMapper
 * @Author XiaoYu
 * @Date 2024/9/7 22:50
 */
public interface CouponBatchMapper extends Mapper<CouponBatchPO> {

    List<CouponBatchPO> selectByBatchIds(List<Long> batchIds);
}
