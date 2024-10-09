package com.xiaoyu.sunny.web.config;

import com.xiaoyu.sunny.service.constants.BaseEnums;
import com.xiaoyu.sunny.service.exception.BaseException;
import com.xiaoyu.sunny.service.exception.ServiceException;
import com.xiaoyu.sunny.web.base.BaseResultVO;
import com.xiaoyu.sunny.web.util.ResultsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @Description: 全局异常处理
 * @Author XiaoYu
 * @Date 2024/9/10 10:02
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfig {

    /**
     * 处理 ServiceException 异常
     */
    @ExceptionHandler(ServiceException.class)
    public BaseResultVO handleServiceException(ServiceException e){
        BaseResultVO result = ResultsUtil.failed(e.getCode(), e.getMessage());
        log.info("ServiceException[code: {}, message: {}]", e.getCode(), e.getMessage());
        return result;
    }


    /**
     * 处理 NoHandlerFoundException 异常.
     * 需配置 [spring.mvc.throw-exception-if-no-handler-found=true]
     * 需配置 [spring.resources.add-mappings=false]
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResultVO handleNotFoundException(NoHandlerFoundException e){
        BaseResultVO result = ResultsUtil.failed(BaseEnums.NOT_FOUND.code(), BaseEnums.NOT_FOUND.desc());
        log.info(e.getMessage());
        return result;
    }

    /**
     * 处理 BaseException 异常
     */
    @ExceptionHandler(BaseException.class)
    public BaseResultVO handleBaseException(BaseException e){
        BaseResultVO result = ResultsUtil.failed(e.getCode(), e.getMessage());
        log.error("BaseException[code: {}, message: {}]", e.getCode(), e.getMessage(), e);
        return result;
    }

    /**
     * 处理 Exception 异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResultVO handleException(Exception e){
        BaseResultVO result = ResultsUtil.failed(BaseEnums.ERROR.code(), BaseEnums.ERROR.desc());
        log.error(e.getMessage(), e);
        return result;
    }
}
