package com.hk.surl.v2.core.generator;

import com.hk.surl.v2.entity.ShortURI;

/**
 * @author : HK意境
 * @ClassName : IShortUrlGenerator
 * @date : 2023/6/3 22:44
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@FunctionalInterface
public interface IShortUrlGenerator<T extends ShortURI> {

    /**
     * 生成短链接实体抽象方法
     * @return {@link ShortURI}
     */
    public T generate(T shortURI);

}
