package com.xiaoyu.sunny.web.base;

import java.io.Serializable;

/**
 * @Description: 前端统一返回对象
 * @Author XiaoYu
 * @Date 2024/9/7 16:51
 */
public class BaseResultVO<T> implements Serializable {

    private static final long serialVersionUID = -1510907275335305936L;

    /**
     * code编码
     */
    private Integer code;
    /**
     * 相关消息
     */
    private String msg;

    /**
     * 相关数据
     */
    private T data;

    public BaseResultVO() {}

    public BaseResultVO(Integer code) {
        this.code = code;
    }

    public BaseResultVO(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public BaseResultVO(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
