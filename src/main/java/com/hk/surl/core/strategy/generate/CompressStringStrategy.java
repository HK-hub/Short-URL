package com.hk.surl.core.strategy.generate;

import com.hk.surl.core.strategy.GenerateStrategy;
import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.enums.CompressStrategy;

/**
 * @author : HK意境
 * @ClassName : CompressStringStrategy
 * @date : 2022/4/18 21:01
 * @description : 使用压缩算法：这里仅仅提供常见的字符串压缩算法
 *                  - gzip 算法
 *                  -
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CompressStringStrategy implements GenerateStrategy {


    @Override
    public String provideShortUrl(Generator generator) {

        // 获取压缩策略
        CompressStrategy compressStrategy = generator.getCompressStrategy();

        if(CompressStrategy.NONE == compressStrategy){
            // 不使用压缩算法

        }else if(CompressStrategy.GZIP_COM == compressStrategy){
            // 采用 gzip 压缩算法

        }

        return null ;
    }
}
