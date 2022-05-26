package com.hk.surl.core.enums;

/**
 * @author : HK意境
 * @ClassName : SyncStrategy
 * @date : 2022/4/18 13:35
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum SyncStrategy {

    // 不使用同步策略
    DISABLE,
    // 使用乐观锁策略
    CAS ,
    // 使用加锁策略
    SYNC,


}
