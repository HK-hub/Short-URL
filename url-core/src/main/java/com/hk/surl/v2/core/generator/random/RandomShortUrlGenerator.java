package com.hk.surl.v2.core.generator.random;

import com.hk.surl.v2.core.config.RandomConfig;
import com.hk.surl.v2.core.generator.AbstractShortUrlGenerator;
import com.hk.surl.v2.entity.ShortURLExt;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.*;
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
    protected RandomConfig randomConfig;

    /**
     * 随机数
     */
    protected Random random;

    public RandomShortUrlGenerator() {

        this.random = this.randomConfig.isThreadSafe() ? ThreadLocalRandom.current() : new Random();
    }

    @Override
    public ShortURLExt preProcess() {
        return null;
    }

    @Override
    public ShortURLExt doGenerate(ShortURLExt shortUrl) {

        // 获取配置
        boolean fast = this.randomConfig.isFast();
        Integer length = this.commonConfig.getLength();

        String code = null;

        // 普通模式：普通模式更加均匀
        char[] pooling = this.randomConfig.getPooling().clone();

        // 是否需要混匀
        if (this.randomConfig.isShuffle()) {
            pooling = this.shuffle(pooling);
        }

        // 生成随机短码
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = pooling[random.nextInt(pooling.length)];
            sb.append(ch);
        }

        // 设置结果
        code = sb.toString();
        this.shortUrl.setShortCode(code)
                .setShortURI(code);
        return this.shortUrl;
    }


    /**
     * 混匀字符串池
     * @param pooling
     * @return
     */
    private char[] shuffle(char[] pooling) {
        // 混匀
        ArrayList<Character> list = new ArrayList<>();
        for (char c : pooling) {
            list.add(c);
        }
        // 扰乱
        Collections.shuffle(list);
        // 放回
        StringBuilder sb = new StringBuilder();
        for (Character c : list) {
            sb.append(c);
        }

        return sb.toString().toCharArray();
    }

    @Override
    public ShortURLExt postProcess() {

        // 在此处使用布隆过滤器进行重复判断

        return null;
    }

}
