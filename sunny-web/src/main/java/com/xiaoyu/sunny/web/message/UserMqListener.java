package com.xiaoyu.sunny.web.message;

import com.xiaoyu.sunny.service.user.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author XiaoYu
 * @Date 2024/9/15 20:06
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "USER_PRODUCER")
public class UserMqListener implements RocketMQListener<UserDTO> {

    @Override
    public void onMessage(UserDTO userDTO) {
        log.info("接收到消息，开始消费..name:{}, phone:{}", userDTO.getUsername(), userDTO.getPhone());
        log.info("----------");
    }
}
