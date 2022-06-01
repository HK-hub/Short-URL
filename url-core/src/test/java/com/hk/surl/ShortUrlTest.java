package com.hk.surl;

import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.enums.strategy.EncryptStrategy;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.entity.ShortUrlExt;
import org.junit.Test;

/**
 * @author : HK意境
 * @ClassName : ShortUrlTest
 * @date : 2022/4/18 21:33
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ShortUrlTest {

    // builder 构造器 V1.0.0 版本
    @Test
    public void testBuilderApiV1_0_0(){

        /*// 获取 generator 生成器
        ShortUrlGenerator generator = new ShortUrlGeneratorBuilder(new ShortUrlExt("https://www.github.com/hk-hub"))
                .length(6)
                .generateStrategy(new RandomStringProvider())
                .enableCache(false)
                .encryptStrategy(EncryptStrategy.NONE)
                .build();

        // 使用生成器进行链接生成
        ShortUrl shortURL = generator.generate();

        System.out.println(shortURL);*/
    }


    // builder 构造器 V1.0.1 版本
    @Test
    public void testBuilderApiV1_0_1(){


    }


}
