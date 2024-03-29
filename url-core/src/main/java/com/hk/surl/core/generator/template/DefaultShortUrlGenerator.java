package com.hk.surl.core.generator.template;

import com.hk.surl.core.enums.strategy.ExpirationStrategy;
import com.hk.surl.core.enums.strategy.LengthStrategy;
import com.hk.surl.core.enums.strategy.SyncStrategy;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.entity.ShortUrlExt;
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

        this.urlExt = new ShortUrlExt();

        // 通过建造者模式 获取生成器
         this.generator = new ShortUrlGeneratorBuilder()
                .provider(new RandomStringProvider())
                .length(LengthStrategy.SMES.getLength())
                .syncStrategy(SyncStrategy.DISABLE)
                .expireStrategy(ExpirationStrategy.FOREVER)
                .build();
    }

    // 域名系统 配置
    public DefaultShortUrlGenerator(String domain){

        // 域名系统：用于生成短链接后的访问前缀部分
        this() ;
        this.urlExt.domain(domain) ;
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
        shortUrl.setShortUrl(this.urlExt.domain()+"/"+shortUrl.getShortUrl());

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
