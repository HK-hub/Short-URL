package com.hk.surl;

import com.hk.surl.core.enums.strategy.ExpirationStrategy;
import com.hk.surl.core.enums.strategy.SyncStrategy;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.domain.entity.ShortUrl;
import org.junit.Test;

/**
 * @author : HK意境
 * @ClassName : GeneratorBuilderTest
 * @date : 2022/6/1 14:13
 * @description : 建造者测试
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class GeneratorBuilderTest {

    static String longUrl = "http://localhost:8080/cloud/45464uy6terju54grfea464uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders4hytejorders/user/15645juytkuy4648/dehtrehgvsfd114545glete";

    // 最小 API 方式
    @Test
    public void minApiBuildTest(){

        // 全部使用默认配置，只需要传入一个 长链接即可
        ShortUrlGenerator generator = new ShortUrlGeneratorBuilder(longUrl).build();

        // 生产短链接
        ShortUrl shortUrl = generator.generate();

        System.out.println(shortUrl);

    }

    // 较为完整的API
    @Test
    public void fullConfigApi(){

        // 通过建造者模式 获取生成器
        ShortUrlGenerator generator = new ShortUrlGeneratorBuilder(longUrl)
                .provider(new RandomStringProvider())
                .length(8)
                .syncStrategy(SyncStrategy.DISABLE)
                .expireStrategy(ExpirationStrategy.ONE_DAYS)
                .build();

        // 生产短链接
        ShortUrl shortUrl = generator.generate();

        ShortUrl shortUrl1 = generator.longUrl("greg").generate();


        System.out.println(shortUrl);
    }
}
