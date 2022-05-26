package com.hk.surl.core.strategy.encrypt;

import com.hk.surl.core.common.EncryptUtil;
import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.enums.EncryptStrategy;

/**
 * @author : HK意境
 * @ClassName : EncryptUrlStrategy
 * @date : 2022/4/18 20:36
 * @description : 加密字符串算法
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class EncryptUrlStrategy{

    public static String encryptString(Generator generator, String source){
        // 获取加密策略
        EncryptStrategy encryptStrategy = generator.getEncryptStrategy();

        if(EncryptStrategy.NONE == encryptStrategy){
            System.out.println("不加密");
            return source;
        }else if(EncryptStrategy.MD5 == encryptStrategy){
            System.out.println("MD5");
            return EncryptUtil.MD5(source);
        }else if(EncryptStrategy.SHA == encryptStrategy || EncryptStrategy.HASH256==encryptStrategy){
            System.out.println("Hash256");
            return EncryptUtil.SHA1(source);
        }else if(EncryptStrategy.BASE64 == encryptStrategy){
            System.out.println("BASE64");
            return EncryptUtil.Base64Encode(source);
        }else{
            //默认不使用加密
        }

        return source ;
    }

}
