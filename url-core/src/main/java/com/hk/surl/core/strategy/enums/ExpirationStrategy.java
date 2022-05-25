package com.hk.surl.core.strategy.enums;


import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : ExpirationStrategy
 * @date : 2022/4/18 13:40
 * @description : 短链接Short-Url 的过期时间，短链接地址能够生效的时间，是否可见等
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum ExpirationStrategy {
    // 永不过期
    NEVER(-1L, null),
    // 一小时过期
    ONE_HOUR(1L, TimeUnit.HOURS),
    // 三小时过期
    THREE_HOUR(3L,TimeUnit.HOURS),
    // 一天过期
    ONE_DAYS(1L,TimeUnit.DAYS),
    // 三天过期
    THREE_DAYS(3L,TimeUnit.DAYS),
    // 五天过期
    FIVE_DAYS(5L,TimeUnit.DAYS),
    // 十天过期
    TEN_DAYS(10L,TimeUnit.DAYS),
    // 一个月过期
    ONE_MONTH(30L, TimeUnit.DAYS);

    //过期时间
    private Long expirationTime ;
    // 过期时间单位
    private TimeUnit timeUnit ;




    ExpirationStrategy(Long expirationTime , TimeUnit timeUnit) {
        this.expirationTime = expirationTime;
        this.timeUnit = timeUnit;
    }


    ExpirationStrategy() {
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }


    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
