package com.hk.surl.core.enums;

/**
 * @author : HK意境
 * @ClassName : EncryptStrategy
 * @date : 2022/4/18 20:11
 * @description : 加密策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum EncryptStrategy {

    // 不加密
    NONE ,
    // 使用MD5 加密
    MD5,
    // 使用hash加密
    HASH256 ,
    //
    SHA ,
    // Base64
    BASE64,
    // AES
    AES ,
    ;

}
