package com.xiaoyu.sunny.service.exception;

/**
 * @Description: 基础异常类
 * @Author XiaoYu
 * @Date 2024/9/7 17:19
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -5354141900545508100L;

    /**
     * 错误编码
     */
     protected int code;

     public BaseException() {}

     public BaseException(String message) {
        super(message);
     }

     public BaseException(int code, String message) {
         super(message);
         this.code = code;
     }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
