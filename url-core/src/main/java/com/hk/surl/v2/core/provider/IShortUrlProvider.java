package com.hk.surl.v2.core.provider;

import com.hk.surl.v2.core.generator.IShortUrlGenerator;
import com.hk.surl.v2.entity.ShortURI;

/**
 * @author : HK意境
 * @ClassName : IShortUrlProvider
 * @date : 2023/6/3 22:32
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@FunctionalInterface
public interface IShortUrlProvider<T extends ShortURI> {

    public T provide(IShortUrlGenerator<T> generator);

}
