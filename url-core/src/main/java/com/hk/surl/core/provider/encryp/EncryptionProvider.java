package com.hk.surl.core.provider.encryp;

import com.hk.surl.core.provider.GenerateStrategy;
import com.hk.surl.core.generator.Generator;
import com.hk.surl.core.strategy.encrypt.EncryptUrlStrategy;
import com.hk.surl.core.enums.EncryptStrategy;

/**
 * @author : HK意境
 * @ClassName : EncryptionProvider
 * @date : 2022/4/18 20:57
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class EncryptionProvider implements GenerateStrategy {

    @Override
    public String provideShortUrl(Generator generator) {
        // 获取源字符串
        String longUrl = generator.getUrlExt().getLongUrl();
        // 获取加密策略
        //EncryptStrategy encryptStrategy = generator.getEncryptStrategy();

        // 拿到的就是目标短链接
        String encryptUrl = EncryptUrlStrategy.encryptString(generator);
        return encryptUrl ;
    }
}
