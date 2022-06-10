package com.hk.surl.web.handler;

import com.hk.surl.common.exception.BaseException;
import com.hk.surl.common.response.ErrorResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.web.exception.UrlWebException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : UrlWebExceptionHandler
 * @date : 2022/6/10 9:29
 * @description : 自定义全局统一异常处理,
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@RestControllerAdvice
@Slf4j
public class UrlWebExceptionHandler {

    
    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/10 9:50
     * @description : 统一处理自定义基础异常
     * @Todo :
     * @apiNote :统一处理自定义基础异常
     * @params : 
         * @param exception 基础异常类
     * @return ErrorResult
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResult baseExceptionHandle(BaseException exception){

        if (exception.getCode() == null) {
            // 没有指定 错误码
            ErrorResult.error(exception.getMessage());
        }
        return ErrorResult.error(exception.getCode(),exception.getMessage());
    }


    /**
     * @methodName : bizExceptionHandle
     * @author : HK意境
     * @date : 2022/6/10 10:04
     * @description : 自定义业务异常处理
     * @Todo :
     * @apiNote :
     * @params :
         * @param exception  UrlWebExceptionHandler类型错误
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @ExceptionHandler(UrlWebException.class)
    public ErrorResult bizExceptionHandle(UrlWebException exception){

        if (exception.getCode() == null) {
            // 没有指定 错误码
            ErrorResult.error(exception.getMessage());
        }
        return ErrorResult.error(exception.getCode(),exception.getMessage());

    }


    /**
     * @methodName : exceptionHandle
     * @author : HK意境
     * @date : 2022/6/10 10:10
     * @description : 统一处理非自定义异常外的所有异常
     * @Todo :
     * @apiNote : 统一处理非自定义异常外的所有异常
     * @params
         * @param exception 非自定义异常类型
     * @return ErrorResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult exceptionHandle(Exception exception){
        // 日志输出
        log.error(exception.getMessage());
        return ErrorResult.error(exception.getMessage());
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/10 10:14
     * @description : 兼容 Validation 校验框架的,参数处理异常
     * @Todo :
     * @apiNote : 请求参数缺失异常
     * @params :
         * @param exception 请求参数缺失异常
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult validationParametersMissingException(MissingServletRequestParameterException exception){

        // 参数缺失异常
        return ErrorResult.error(ResultCode.PARAMETER_ERROR, "request parameters : " +exception.getParameterType() +" "
                + exception.getParameterName() + " missing or null");

    }


    /**
     * @methodName : validationParametersNotValidException
     * @author : HK意境
     * @date : 2022/6/10 19:52
     * @description : 参数校验框架：请求参数校验异常
     * @Todo :
     * @apiNote : 兼容Validation校验框架：参数效验异常处理器
     * @params :
         * @param exception 方法参数校验错误异常
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult validationParametersNotValidException(MethodArgumentNotValidException exception){

        log.error(exception.getMessage(), exception);
        // 获取异常信息
        BindingResult bindingResult = exception.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exception.hasErrors()) {
            // 获取全部的错误信息
            List<ObjectError> allErrors = exception.getAllErrors();
            // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
            FieldError error = (FieldError)allErrors.get(0);
            // 返回错误信息
            return ErrorResult.error(ResultCode.PARAMETER_ERROR,error.getDefaultMessage());
        }

        // 异常中没有错误信息
        return ErrorResult.error(ResultCode.PARAMETER_ERROR, "request parameters validation exception!");

    }



}
