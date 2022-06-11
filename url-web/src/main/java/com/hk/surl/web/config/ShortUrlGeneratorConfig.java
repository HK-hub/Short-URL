package com.hk.surl.web.config;

import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.generator.template.DefaultShortUrlGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : HK意境
 * @ClassName : ShortUrlGeneratorConfig
 * @date : 2022/6/11 12:50
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class ShortUrlGeneratorConfig {


    /**
     * 将 shorUrlGenerator 将默认的短链接生成器注入到容器中
     * @return ShortUrlGenerator
     */
    @Bean
    public DefaultShortUrlGenerator getDefaultShortUrlGenerator(){
        return new DefaultShortUrlGenerator();
    }


}
