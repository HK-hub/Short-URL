package com.hk.surl.core.common.excption;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : SUrlCoreExcption
 * @date : 2022/5/30 13:33
 * @description : 核心模块异常
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class SUrlCoreExcption extends RuntimeException{


    // 错误信息
    private String message ;
    // 错误代码
    private Integer errorCode ;

    public SUrlCoreExcption(String message , Integer code){
        this.errorCode = code;
        this.message = message ;
    }

    public SUrlCoreExcption(ExcptionInstance excption){
        this(excption.getMessage(),excption.getErrorCode());
    }

    public static enum ExcptionInstance{

        LeafSegment_DB_Error("没有剩余号码可以发送, 可能是出现了数据库服务不可用，数据库号段资源耗尽",100101),
        LeafSegment_BIZ_Error("没有指定业务标识的发号器可以使用",100102)

                ;

        // 错误信息
        private String message ;
        // 错误代码
        private Integer errorCode ;

        ExcptionInstance(String message, Integer errorCode) {
            this.message = message;
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
            this.errorCode = errorCode;
        }

    }


}
