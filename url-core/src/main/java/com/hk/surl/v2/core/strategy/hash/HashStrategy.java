package com.hk.surl.v2.core.strategy.hash;

/**
 * @author : HK意境
 * @ClassName : HashStrategy
 * @date : 2023/6/5 19:50
 * @description : hash 接口，用于枚举策略的实现
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface HashStrategy {

    public String hash(String plain);


}
