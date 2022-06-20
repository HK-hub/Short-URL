package com.hk.surl.web.config;

import com.hk.surl.web.interceptor.FaviconInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

/**
 * @author : HK意境
 * @ClassName : WebConfig
 * @date : 2022/6/12 22:16
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域配置会覆盖默认的配置，
     * 因此需要实现addResourceHandlers方法，增加默认配置静态路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 设置允许的方法
                .allowedMethods("*")
                // 是否允许证书（cookies）
                .allowCredentials(true);

    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new FaviconInterceptor())
                .addPathPatterns("/favicon.ico");
    }






}
