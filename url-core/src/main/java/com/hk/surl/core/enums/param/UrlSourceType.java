package com.hk.surl.core.enums.param;

/**
 * @author : HK意境
 * @ClassName : UrlSourceType
 * @date : 2022/5/31 22:58
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum UrlSourceType {
    // url 链接，base64 数据，二维码
    URL("url", 0),BASE64("base64",1),QRCODE("qrcode",2)
    ;

    // url 路径字符串的 类型
    private String type ;
    private Integer code ;

    UrlSourceType(String type) {
        this.type = type;
    }

    UrlSourceType(String type, Integer code) {
        this.type = type;
        this.code = code;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
