package com.hk.surl.core.provider;

import com.hk.surl.core.generator.Generator;

/**
 * @author : HK意境
 * @ClassName : GenerateStrategy
 * @date : 2022/4/18 14:00
 * @description : 短链接生成算法策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface GenerateStrategy {

    public String provideShortUrl(Generator generator );


}
