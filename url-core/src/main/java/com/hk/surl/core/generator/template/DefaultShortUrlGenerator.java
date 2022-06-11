package com.hk.surl.core.generator.template;

import com.hk.surl.core.enums.strategy.ExpirationStrategy;
import com.hk.surl.core.enums.strategy.LengthStrategy;
import com.hk.surl.core.enums.strategy.SyncStrategy;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.domain.entity.ShortUrl;
import lombok.Data;

import javax.sql.DataSource;

/**
 * @author : HK意境
 * @ClassName : DefaultShortUrlGenerator
 * @date : 2022/6/10 21:47
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class DefaultShortUrlGenerator extends ShortUrlGenerator{

    // 默认 provider ：随机字符串provider
    private ShortUrlGenerator generator ;

    public DefaultShortUrlGenerator() {
        // 通过建造者模式 获取生成器
         this.generator = new ShortUrlGeneratorBuilder()
                .provider(new RandomStringProvider())
                .length(LengthStrategy.SMES.getLength())
                .syncStrategy(SyncStrategy.DISABLE)
                .expireStrategy(ExpirationStrategy.FOREVER)
                .build();
    }

    public DefaultShortUrlGenerator(ShortUrlGenerator generator){
        this.generator = generator;
    }


    /**
     * @methodName : generate
     * @author : HK意境
     * @date : 2022/6/11 12:22
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param longUrl 转换的长链接字符串
     * @return ShortUrl 短链接实体
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public ShortUrl generate(String longUrl){
        // 使用默认provider 进行生产
        ShortUrl shortUrl = this.generator.longUrl(longUrl).generate();
        return shortUrl ;
    }


    public DefaultShortUrlGenerator(ShortUrlGenerator generator, DataSource dataSource) {
        this.generator = generator;
    }

    public ShortUrlGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(ShortUrlGenerator generator) {
        this.generator = generator;
    }

}
