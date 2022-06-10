package com.hk.surl.core.generator.template;

import com.hk.surl.core.enums.strategy.ExpirationStrategy;
import com.hk.surl.core.enums.strategy.LengthStrategy;
import com.hk.surl.core.enums.strategy.SyncStrategy;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.builder.ShortUrlGeneratorBuilder;
import com.hk.surl.core.provider.random.RandomStringProvider;
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
public class DefaultShortUrlGenerator {

    private ShortUrlGenerator generator ;

    private DataSource dataSource ;


    public DefaultShortUrlGenerator() {
        // 通过建造者模式 获取生成器
         this.generator = new ShortUrlGeneratorBuilder()
                .provider(new RandomStringProvider())
                .length(LengthStrategy.SMES.getLength())
                .syncStrategy(SyncStrategy.DISABLE)
                .expireStrategy(ExpirationStrategy.FOREVER)
                .build();
    }

    public DefaultShortUrlGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
        generator.
    }

    public DefaultShortUrlGenerator(ShortUrlGenerator generator, DataSource dataSource) {
        this.generator = generator;
        this.dataSource = dataSource;
    }


    public ShortUrlGenerator getGenerator() {
        return generator;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setGenerator(ShortUrlGenerator generator) {
        this.generator = generator;
    }

    public void setDataSource(DataSource dataSource) {

        this.dataSource = dataSource;
    }
}
