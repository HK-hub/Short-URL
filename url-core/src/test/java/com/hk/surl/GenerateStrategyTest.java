package com.hk.surl;

import com.hk.surl.core.generator.Generator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.enums.LengthStrategy;
import com.hk.surl.core.provider.distributed.SnowFlakeProvider;
import com.hk.surl.domain.entity.ShortUrl;
import org.junit.Test;

/**
 * @author : HK意境
 * @ClassName : GenerateStrategyTest
 * @date : 2022/4/18 18:52
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class GenerateStrategyTest {

    @Test
    public void randomStrategyTest(){
        Generator generator = new ShortUrlGeneratorBuilder()
                .generateStrategy(new SnowFlakeProvider())
                .length(LengthStrategy.LARGE_ENTERPRISE.getLength()).build();
        ShortUrl shortURL = generator.generate();
        System.out.println(shortURL);

    }


}
