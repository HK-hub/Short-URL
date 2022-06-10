package com.hk.surl.common.exception;

import com.hk.surl.common.response.ResultCode;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : BaseException
 * @date : 2022/6/10 9:37
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class BaseException extends RuntimeException{

    // 是否成功
    protected Boolean isSuccess = false;
    // 错误代码
    protected Integer code ;
    // 错误信息
    protected String msg ;
    // 错误时间
    protected LocalDateTime time = LocalDateTime.now();

    // 错误链路
    protected String traceId ;


    // 基本异常类型错误码
    private static final int BASE_EXCEPTION_CODE = ResultCode.SERVER_ERROR.code();
    // 基本类型异常错误消息
    private static final String BASE_EXCEPTION_MESSAGE = ResultCode.SERVER_ERROR.message();


    // 服务器内部错误异常
    public BaseException() {
        super(BASE_EXCEPTION_MESSAGE);
        this.code = BASE_EXCEPTION_CODE;
        this.msg = BASE_EXCEPTION_MESSAGE;
   }

    public BaseException(String message) {
        super(message);
        this.code = BASE_EXCEPTION_CODE;
        this.msg = message;
    }

    public BaseException(ResultCode resultCode) {
        super(resultCode.message());
        this.code = resultCode.code();
        this.msg = resultCode.message();
        this.isSuccess = resultCode.success();
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.code = BASE_EXCEPTION_CODE;
        this.msg = BASE_EXCEPTION_MESSAGE;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = BASE_EXCEPTION_CODE;
        this.msg = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }



}