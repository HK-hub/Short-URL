package com.hk.surl.web.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : MybatisPlusConfig
 * @date : 2022/6/1 16:39
 * @description : 配置分页插件，自动注入
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Configuration
public class MybatisPlusConfig {

    // 自动注入功能
    @Bean
    public MetaObjectHandler getMetaObjectHandler(){

        MetaObjectHandler metaObjectHandler = new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject,"createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class, LocalDateTime.now());

            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject,"updateTime", LocalDateTime.class, LocalDateTime.now());
            }
        };

        return metaObjectHandler;
    }


    // 分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }


}
