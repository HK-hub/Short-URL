package com.hk.surl.core.strategy.generate;

import com.hk.surl.core.strategy.GenerateStrategy;
import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.encrypt.EncryptUrlStrategy;
import com.hk.surl.core.strategy.enums.EncryptStrategy;

/**
 * @author : HK意境
 * @ClassName : EncryptionStrategy
 * @date : 2022/4/18 20:57
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class EncryptionStrategy implements GenerateStrategy {

    @Override
    public String provideShortUrl(Generator generator) {
        // 获取源字符串
        String longUrl = generator.getUrlExt().getLongUrl();
        // 获取加密策略
        EncryptStrategy encryptStrategy = generator.getEncryptStrategy();
        String encryptUrl = EncryptUrlStrategy.encryptString(generator, longUrl);
        return encryptUrl ;
    }
}
