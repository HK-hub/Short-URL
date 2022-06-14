package com.hk.surl.web.config;

import com.hk.surl.web.aop.SysLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author : HK意境
 * @ClassName : AopAspectConfig
 * @date : 2022/6/14 14:38
 * @description : 面向切面编配置类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class AopAspectConfig {

    @Bean
    public SysLogAspect sysLogAspect(){
        return new SysLogAspect();
    }


}
