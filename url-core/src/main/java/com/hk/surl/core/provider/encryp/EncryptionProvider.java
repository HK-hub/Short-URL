package com.hk.surl.core.provider.encryp;

import com.hk.surl.core.provider.GenerateProvider;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.strategy.encrypt.EncryptUrlStrategy;

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
public class EncryptionProvider implements GenerateProvider {

    @Override
    public String provideShortUrl(ShortUrlGenerator generator) {
        // 获取源字符串
        String longUrl = generator.getUrlExt().longUrl();
        // 获取加密策略
        //EncryptStrategy encryptStrategy = generator.getEncryptStrategy();

        // 拿到的就是目标短链接
        String encryptUrl = EncryptUrlStrategy.encryptString(generator);
        return encryptUrl ;
    }
}
