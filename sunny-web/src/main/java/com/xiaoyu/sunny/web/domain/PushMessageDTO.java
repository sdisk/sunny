package com.xiaoyu.sunny.web.domain;

import lombok.Data;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 19:35
 */
@Data
public class PushMessageDTO {

    private Long userId;
    private String phoneNumber;
    //消息内容
    //[xx公司] 亲爱的用户你好，由于你是优质客户，特意发放一张[xx]券，请查收。
    // message_template -> [{companyName}公司] 亲爱的用户你好，由于你是优质客户，特意发放一张{couponName}券，请查收。
    private String message;
    private PushMessageEventType eventType;

}
