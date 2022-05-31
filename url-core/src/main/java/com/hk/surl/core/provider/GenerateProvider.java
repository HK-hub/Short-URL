package com.hk.surl.core.provider;

import com.hk.surl.core.generator.ShortUrlGenerator;

/**
 * @author : HK意境
 * @ClassName : GenerateProvider
 * @date : 2022/4/18 14:00
 * @description : 短链接生成算法策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@FunctionalInterface
public interface GenerateProvider {

    public abstract String provideShortUrl(ShortUrlGenerator generator );


}
