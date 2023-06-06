package com.hk.surl.v2.core.generator.random;

import com.hk.surl.v2.core.config.RandomConfig;
import com.hk.surl.v2.core.generator.AbstractShortUrlGenerator;
import com.hk.surl.v2.entity.ShortURLExt;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : HK意境
 * @ClassName : RandomShortUrlGenerator
 * @date : 2023/6/6 22:20
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class RandomShortUrlGenerator extends AbstractShortUrlGenerator<ShortURLExt> {

    /**
     * 随机策略配置
     */
    protected RandomConfig config;

    /**
     * 随机数
     */
    protected Random random;

    public RandomShortUrlGenerator() {

        this.random = this.config.isThreadSafe() ? ThreadLocalRandom.current() : new Random();
    }

    @Override
    public ShortURLExt preProcess() {
        return null;
    }

    @Override
    public ShortURLExt doGenerate(ShortURLExt shortUrl) {

        // 获取配置
        boolean fast = this.config.isFast();
        Integer length = this.commonConfig.getLength();

        String code = null;

        // 普通模式：普通模式更加均匀
        char[] pooling = this.config.getPooling();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = pooling[random.nextInt(pooling.length)];
            sb.append(ch);
        }

        code = sb.toString();
        this.shortUrl.setShortCode(code)
                .setShortURI(code);

        return this.shortUrl;
    }

    @Override
    public ShortURLExt postProcess() {
        return null;
    }
}
