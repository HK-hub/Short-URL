package com.hk.surl.core.strategy.encrypt;


import com.hk.surl.core.common.util.EncryptUtil;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.enums.strategy.EncryptStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptUrlStrategy.class);


    public static String encryptString(ShortUrlGenerator generator){

        // 获取长链接
        String longUrl = generator.urlExt().longUrl();

        // 获取加密策略
        EncryptStrategy encryptStrategy = generator.encryptStrategy();

        // 根据加密策略选择加密算法
        if(EncryptStrategy.NONE == encryptStrategy){
            // 不加密
            return longUrl;

        }else if(EncryptStrategy.MD5 == encryptStrategy){
            //System.out.println("MD5");
            return EncryptUtil.MD5(longUrl);

        }else if(EncryptStrategy.SHA == encryptStrategy || EncryptStrategy.HASH256==encryptStrategy){
            //System.out.println("Hash256");
            return EncryptUtil.SHA1(longUrl);

        }else if(EncryptStrategy.BASE64 == encryptStrategy){
            //System.out.println("BASE64");
            return EncryptUtil.Base64Encode(longUrl);
        }else{
            //默认不使用加密
        }

        return longUrl ;
    }

}
