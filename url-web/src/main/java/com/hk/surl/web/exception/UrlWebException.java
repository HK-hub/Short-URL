package com.hk.surl.web.exception;

import com.hk.surl.common.exception.BaseException;
import com.hk.surl.common.response.ResultCode;
import lombok.Data;

/**
 * @author : HK意境
 * @ClassName : UrlWebException
 * @date : 2022/6/10 9:23
 * @description : Web 模块自定义业务异常
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class UrlWebException extends BaseException {

    public UrlWebException(Integer code, String msg) {
        super(code,msg);
    }


    public UrlWebException(ResultCode resultCode){
        super(resultCode);
    }


}
