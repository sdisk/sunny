package com.xiaoyu.sunny.service.exception;

/**
 * @Description: Service层异常
 * @Author XiaoYu
 * @Date 2024/9/7 17:21
 */
public class ServiceException extends BaseException{

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }
}
