package com.xiaoyu.sunny.web.message.consumer;

import cn.hutool.json.JSONUtil;
import com.xiaoyu.sunny.dao.coupon.entity.CouponInfoPO;
import com.xiaoyu.sunny.service.enums.CouponStatusEnum;
import com.xiaoyu.sunny.web.domain.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 20:58
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "ORDER_PRODUCER")
public class OrderMqListener implements RocketMQListener<OrderDTO> {

    @Override
    public void onMessage(OrderDTO orderDTO) {
        log.info("接收到消息，开始消费..message={}", JSONUtil.toJsonStr(orderDTO));
        //根据支付状态，更新对应的使用优惠券列表中优惠券的状态
        List<Long> couponIdList = orderDTO.getCouponIdList();
        List<CouponInfoPO> couponInfoPOList = couponIdList.stream().map((couponId) -> {
            CouponInfoPO couponInfoPO = new CouponInfoPO();
            if (orderDTO.isSuccess()) {
                couponInfoPO.setCouponId(couponId);
                couponInfoPO.setCouponStatus(CouponStatusEnum.USED.getCode());
            } else {
                couponInfoPO.setCouponId(couponId);
                couponInfoPO.setCouponStatus(CouponStatusEnum.RECEIVE.getCode());
            }
            return couponInfoPO;
        }).collect(Collectors.toList());

        //保存数据
        saveCouponInfoPOList(couponInfoPOList);
        log.info("----处理消息结束------");
    }

    private void saveCouponInfoPOList(List<CouponInfoPO> couponInfoPOList) {

    }
}
