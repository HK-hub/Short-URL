package com.hk.surl.core.generator;

import com.hk.surl.core.common.util.DateTimeUtil;
import com.hk.surl.core.enums.strategy.*;
import com.hk.surl.core.provider.GenerateProvider;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.entity.ShortUrlExt;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : ShortUrlGenerator
 * @date : 2022/4/18 18:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class ShortUrlGenerator implements IShortUrlGenerator {

    // 短链接扩展实体
    protected ShortUrlExt urlExt ;

    // 选择的算法策略 : 默认使用 randomStringProvider
    protected GenerateProvider provider = new RandomStringProvider();

    // 是否开启缓存策略, 该缓存策略是指是否缓存在 Redis 里面, 如果有指定Short-URL过期策略的，则动态更具时间决定是否存放在缓存中
    protected Boolean enableCache = false;

    // 其他配置
    // 生成后的 短链接 URI 部分的长度
    protected Integer length = LengthStrategy.SMES.getLength();

    // 选择Short-URL 生成后的有效时间
    protected ExpirationStrategy expireStrategy = ExpirationStrategy.FOREVER;

    // 是否选择使用线程安全的模式：这里准备弃用了
    protected SyncStrategy syncStrategy = SyncStrategy.DISABLE;

    // 是否需要对生成后的 短链接 再次进行加密-> 这里准备弃用了
    protected EncryptStrategy encryptStrategy=EncryptStrategy.NONE ;

    // 采用的压缩策略: 默认采用不压缩-> 这里准备弃用了
    protected CompressStrategy compressStrategy = CompressStrategy.NONE ;


    // 构造函数
    public ShortUrlGenerator(GenerateProvider provider){
        this.provider = provider ;
    }


    @Override
    public ShortUrl generate() {
        ShortUrl shortURL = new ShortUrl() ;

        // 线程安全配置
        if(SyncStrategy.SYNC == this.syncStrategy){
            shortURL = this.doGenerateWithSync();
        }else{
            shortURL = this.doGenerateWithoutSync();
        }

        // 加密策略进行加密: 这里被放弃了，如果选择加密，那么请直接使用 encryptionProvider 进行生成
        //String encryptString = EncryptUrlStrategy.encryptString(this);

        // 过期配置
        this.doExpirationStrategy(shortURL);

        return shortURL ;
    }


    /**
     * @methodName :doGenerateWithSync
     * @author : HK意境
     * @date : 2022/4/18 16:44
     * @description : 线程安全策略, 线程安全得生产
     * @Todo :
     * @params :
     * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    private synchronized ShortUrl  doGenerateWithSync(){
        return doGenerateWithoutSync() ;
    }

    // 不启用线程安全策略
    private ShortUrl doGenerateWithoutSync(){
        // 目标链接：短链接
        String targetUrl = provider.provideShortUrl(this);

        // 设置创建时间，跟新时间
        ShortUrl shortUrl = new ShortUrl(targetUrl);

        return shortUrl;
    }


    // 缓存，过期时间配置
    // 再基础层面做缓存，不太符合实际开发规范，不容易进行外部扩展，所以这里不建议使用
    private void doExpirationStrategy(ShortUrl shortURL){

        LocalDateTime expirationTime = LocalDateTime.MAX ;

        // 永久有效
        if (this.expireStrategy.equals(ExpirationStrategy.FOREVER)) {
            // 设置失效时间为时间最大值
            //shortURL.setExpirationTime(LocalDateTime.MAX);

        }else{
            // 根据时间单位进行设置
            Long time = this.expireStrategy.getExpirationTime();
            TimeUnit timeUnit = this.expireStrategy.getTimeUnit();
            // 转换为 毫秒值，
            long mills = TimeUnit.MILLISECONDS.convert(time, timeUnit);
            // 根据毫秒值生产过期时间
            expirationTime = DateTimeUtil.LocalDateTimePlusTimeUnit(LocalDateTime.now(), time, timeUnit);
        }

        // 设置过期时间
        shortURL.setExpirationTime(expirationTime);

    }




}
