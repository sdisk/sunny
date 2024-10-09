package com.xiaoyu.sunny.service.base;

import org.springframework.aop.framework.AopContext;

/**
 * @Description: 自己代理对象
 * @Author XiaoYu
 * @Date 2024/9/7 22:34
 */
public class ProxySelf<T> {

    /**
     * 取得当前对象的代理.
     *
     * @return 代理对象,如果未被代理,则抛出 IllegalStateException
     */
    @SuppressWarnings("unchecked")
    T self() {
        return (T) AopContext.currentProxy();
    }
}
