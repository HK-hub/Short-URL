package com.hk.surl.common.response;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 数据响应对象
 * {
 *     success：成功,
 *     code   ：响应码,
 *     message:返回信息,
 *     //响应数据
 *     data:{
 *
 *     }
 *
 * }
 *
 *
 * @author HK意境**/

@Data
@ToString
@NoArgsConstructor
public class ResponseResult<T extends Object> {


    //是否成功
    private boolean success;
    //返回码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;
    // 响应时间
    private LocalDateTime dateTime = LocalDateTime.now() ;
    // 响应ID： 后期进行链路追踪
    private String traceId;

    // 静态返回对象
    public static ResponseResult SuccessResponse = new ResponseResult(ResultCode.SUCCESS,"ok");
    public static ResponseResult FailResponse = new ResponseResult(ResultCode.FAIL,"failed");
    public static ResponseResult ErrorResponse = new ResponseResult(ResultCode.REMOTE_INTERFACE_ERROR,"exception");

    // 快捷返回对象
    public static <T> ResponseResult<T> SUCCESS(){
        return new ResponseResult(ResultCode.SUCCESS);
    }

    public static <T> ResponseResult<T> ERROR(){
        return new ResponseResult(ResultCode.SERVER_ERROR);
    }

    public static <T> ResponseResult<T> FAIL(){
        return new ResponseResult(ResultCode.FAIL);
    }


    public ResponseResult(boolean flag){
        if (flag){
            // 成功
            this.setResultCode(ResultCode.SUCCESS);
        }else{
            this.setResultCode(ResultCode.FAIL);
        }
    }

    public ResponseResult(ResultCode code) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
    }

    public ResponseResult(ResultCode code, T data) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        this.data = data;
    }

    public ResponseResult(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public ResponseResult setResultCode(ResultCode code){
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        return this;
    }

    public ResponseResult setData(T data){
        this.data = data ;
        return this;
    }



}
