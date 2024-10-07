package com.xiaoyu.sunny.web.util;

import com.xiaoyu.sunny.web.base.BaseResultVO;

/**
 * @Description: BaseResultVO生成工具类
 * @Author XiaoYu
 * @Date 2024/9/7 17:06
 */
public class ResultsUtil {

    static int SUCCESS_CODE = 0;
    static int FAIL_CODE = 1;
    static String SUCCESS_MSG = "成功";
    static String FAIL_MSG = "服务开小差了";


    public static BaseResultVO successed(){
        return successedWith(SUCCESS_CODE, SUCCESS_MSG, null);
    }
    public static <T> BaseResultVO<T> successed(T data){
        return successedWith(SUCCESS_CODE, SUCCESS_MSG, data);
    }
    public static <T> BaseResultVO<T> successedWith(int code, String msg, T data){
        return new BaseResultVO(code, msg, data);
    }
    public static BaseResultVO failed(int code){
        return failedWith(code, FAIL_MSG, null);
    }

    public static BaseResultVO failed(String msg){
        return failedWith(FAIL_CODE, msg, null);
    }

    public static BaseResultVO failed(int code, String msg){
        return failedWith(code, msg, null);
    }

    public static <T>BaseResultVO<T> failedWith(int code, String msg, T data){
        return new BaseResultVO(code, msg, data);
    }
}
