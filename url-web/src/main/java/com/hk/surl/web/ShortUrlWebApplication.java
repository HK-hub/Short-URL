package com.hk.surl.web;

import com.hk.surl.web.aop.EnableSysLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author : HK意境
 * @ClassName : ShortUrlWebApplication
 * @date : 2022/6/1 16:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

@MapperScan(basePackages = "com.hk.surl.domain.mapper")
@EnableAsync
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "com.hk.surl")
public class ShortUrlWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlWebApplication.class, args);
    }

}
