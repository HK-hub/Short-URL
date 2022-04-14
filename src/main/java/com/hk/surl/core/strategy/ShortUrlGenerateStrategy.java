package com.hk.surl.core.strategy;

import com.hk.surl.entity.ShortUrlExt;

/**
 * @author : HK意境
 * @ClassName : ShortUrlGenerateStrategy
 * @date : 2022/4/14 9:52
 * @description : 短链接生成策略
 * @Todo : 这是一个模板方法
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface ShortUrlGenerateStrategy {

    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/4/14 9:54
     * @description : 根据工厂模式提供的参数，根据策略，生成短链接
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public ShortUrlExt generate();


}
