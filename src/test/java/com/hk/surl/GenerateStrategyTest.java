package com.hk.surl;

import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.ShortUrlGeneratorBuilder;
import com.hk.surl.core.strategy.enums.LengthStrategy;
import com.hk.surl.core.strategy.generate.RandomStringStrategy;
import com.hk.surl.core.strategy.generate.SnowFlakeStrategy;
import com.hk.surl.entity.ShortURL;
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
                .generateStrategy(new SnowFlakeStrategy())
                .length(LengthStrategy.LARGE_ENTERPRISE.getLength()).build();
        ShortURL shortURL = generator.generate();
        System.out.println(shortURL);

    }


}
