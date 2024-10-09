package com.xiaoyu.sunny.service.constants;

import com.xiaoyu.sunny.service.base.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 基础枚举值
 * @Author XiaoYu
 * @Date 2024/9/7 17:14
 */
public enum BaseEnums implements BaseEnum<Integer, String> {
    SUCCESS(0, "请求成功"),
    FAILURE(1, "请求失败"),
    OPERATION_SUCCESS(0, "操作成功"),
    OPERATION_FAILURE(1, "操作失败"),
    ERROR(3000, "系统异常"),
    NOT_FOUND(4000, "请求资源不存在"),
    FORBIDDEN(4001, "无权限访问"),
    VERSION_NOT_MATCH(4002, "记录版本不存在或不匹配"),
    PARAMETER_NOT_NULL(3001, "参数不能为空");

    private int code;

    private String desc;

    private static Map<Integer, String> allMap = new HashMap<>();

    BaseEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    static {
        for(BaseEnums enums : BaseEnums.values()){
            allMap.put(enums.code, enums.desc);
        }
    }

    public String desc(String code) {
        return allMap.get(code);
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }
}
