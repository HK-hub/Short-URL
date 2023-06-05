package com.hk.surl.v2.core.strategy.cache;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : CacheStrategy
 * @date : 2023/6/4 0:13
 * @description : 缓存策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class CacheStrategy {

    /**
     * 缓存类型
     */
    protected StorageType storageType;

    /**
     * 到期期时间: 可以通过指定具体的过期时间
     */
    protected LocalDateTime expireTime;

    /**
     * 过期时间：指定缓存时长， 默认七天
     */
    protected Long ttl = 7L;


    /**
     * 过期时间单位：默认天
     */
    protected TimeUnit timeUnit = TimeUnit.DAYS;


    /**
     * 缓存选择类型
     */
    static enum StorageType {

        // 不用缓存
        NONE,
        // 用内存作为缓存，采用Map类型
        Simple,
        // Caffeine中间件缓存
        Caffeine,
        // Redis 进行缓存
        Redis,

    }

}
