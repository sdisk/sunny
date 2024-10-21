package com.xiaoyu.sunny.web.domain;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author XiaoYu
 * @Date 2024/10/21 19:37
 */
public enum PushMessageEventType {
    ISSUANCE(1, "Issuance");

    private int code;
    private String desc;
    private PushMessageEventType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }

}
