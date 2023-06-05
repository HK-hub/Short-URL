package com.hk.surl.v2.core.generator;

import com.hk.surl.v2.core.config.CommonConfig;
import com.hk.surl.v2.entity.ShortURI;

/**
 * @author : HK意境
 * @ClassName : AbstractShortUrlGenerator
 * @date : 2023/6/3 23:04
 * @description : 如果需要自定义实现自己的生成器，建议继承此类进行设计
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public abstract class AbstractShortUrlGenerator<T extends ShortURI> implements IShortUrlGenerator<T> {


    /**
     * 目标短链接实体
     */
    protected T shortUrl;

    /**
     * 通用配置
     */
    protected CommonConfig commonConfig;


    /**
     * 生成短链接之前的前置处理步骤，准备步骤等操作
     * @return T {@link ShortURI}
     */
    public abstract T preProcess();


    /**
     * 生成短链接实体模板方法
     * @return T {@link ShortURI}
     */
    @Override
    public T generate() {

        // 前置处理
        this.preProcess();

        // 生成方法
        this.doGenerate();

        // 后置处理
        this.postProcess();

        return this.shortUrl;
    }


    /**
     * 生成短链接
     * @return
     */
    public abstract T doGenerate();


    /**
     * 生成短链接数据之后的后置处理
     * @return T {@link ShortURI}
     */
    public abstract T postProcess();

}
