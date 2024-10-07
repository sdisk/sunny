package com.xiaoyu.sunny.service.base;


/**
 * @Description: 基础枚举接口
 * @Author XiaoYu
 * @Date 2024/9/7 17:12
 */
public interface BaseEnum<K, V> {
    /**
     * 编码
     * @return
     */
    K code();

    /**
     * 描述
     * @return
     */
    V desc();
}
