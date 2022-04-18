package com.hk.surl.core.strategy;

import com.hk.surl.entity.ShortURL;

/**
 * @author : HK意境
 * @ClassName : IShortUrlGenerator
 * @date : 2022/4/18 13:29
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface IShortUrlGenerator {

    /**
     * @methodName : 生成方法
     * @author : HK意境
     * @date : 2022/4/18 13:29
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public ShortURL generate();


}