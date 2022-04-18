package com.hk.surl;

import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.ShortUrlGeneratorBuilder;
import com.hk.surl.core.strategy.enums.EncryptStrategy;
import com.hk.surl.core.strategy.generate.RandomStringStrategy;
import com.hk.surl.entity.ShortURL;
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

    @Test
    public void test(){

        // 获取 generator 生成器
        Generator generator = new ShortUrlGeneratorBuilder(new ShortUrlExt("https://www.github.com/hk-hub"))
                .length(6)
                .generateStrategy(new RandomStringStrategy())
                .enableCache(false)
                .encryptStrategy(EncryptStrategy.NONE)
                .build();

        // 使用生成器进行链接生成
        ShortURL shortURL = generator.generate();

        System.out.println(shortURL);
    }


}
