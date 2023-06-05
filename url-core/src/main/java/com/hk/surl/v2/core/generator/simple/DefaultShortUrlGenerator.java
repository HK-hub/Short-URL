package com.hk.surl.v2.core.generator.simple;


import com.hk.surl.v2.core.config.RecyclingModeConfig;
import com.hk.surl.v2.core.generator.AbstractShortUrlGenerator;
import com.hk.surl.v2.entity.ShortURLExt;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : HK意境
 * @ClassName : DefaultShortUrlGenerator
 * @date : 2023/6/3 23:33
 * @description : 默认短链接生成器，采用 64 进制算法
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
public class DefaultShortUrlGenerator extends AbstractShortUrlGenerator<ShortURLExt> {


    /**
     * 进制配置
     */
    protected RecyclingModeConfig recyclingModeConfig = new RecyclingModeConfig();


    /**
     * 进制累加器
     */
    protected AtomicLong adder;


    /**
     * 初始化adder
     */
    public DefaultShortUrlGenerator() {

        // 初始化实体
        this.shortUrl = new ShortURLExt();

        Long initNumber = this.recyclingModeConfig.getInitNumber();
        this.adder = new AtomicLong(initNumber);
    }


    /**
     *
     * @return
     */
    @Override
    public ShortURLExt preProcess() {
        return null;
    }

    @Override
    public ShortURLExt doGenerate() {

        // 循环取余
        long number = this.adder.incrementAndGet();
        int length = this.recyclingModeConfig.getPooling().length;

        // builder
        StringBuilder sb = new StringBuilder();

        while (number != 0L) {
            int mode = (int) (number % length);
            number = number / length;

            sb.append(this.recyclingModeConfig.getPooling()[mode]);
        }

        // 设置短链接码
        String code = sb.toString();
        this.shortUrl.setShortCode(code)
                .setShortURI(code);

        return this.shortUrl;
    }

    @Override
    public ShortURLExt postProcess() {
        log.info("short code:{}", this.shortUrl.getShortCode());
        return null;
    }
}
