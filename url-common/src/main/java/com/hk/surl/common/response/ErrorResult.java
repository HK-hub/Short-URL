package com.hk.surl.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ErrorResult implements Serializable {

    private boolean success = false;
    private Integer code;
    private String message;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
    @JsonIgnore
    private ResultCode resultCode;


    public static ErrorResult error() {
        ErrorResult result = new ErrorResult();
        result.setResultCode(ResultCode.SERVER_ERROR);
        return result;
    }

   public static ErrorResult error(String message) {
       ErrorResult result = new ErrorResult();
       result.setCode(ResultCode.SERVER_ERROR.code());
       result.setMessage(message);
       return result;
   }


   public static ErrorResult error(Integer code, String message) {
       ErrorResult result = new ErrorResult();
       result.setCode(code);
       result.setMessage(message);
       return result;
   }


   public static ErrorResult error(ResultCode resultCode, String message) {
       ErrorResult result = new ErrorResult();
       result.setResultCode(resultCode);
       result.setMessage(message);
       return result;
   }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public LocalDateTime getTime() {
        return time;
    }
}
