package com.hk.surl.core.strategy.enums;

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
    NEVER,
    // 三天过期
    THREE_DAYS,
    // 五天过期
    FIVE_DAYS,
    // 十天过期
    TEN_DAYS,
    // 一个月过期
    ONE_MONTH,

}
