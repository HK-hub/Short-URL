package com.hk.surl.v2.core.generator.hash;

import com.hk.surl.v2.core.config.HashModeConfig;
import com.hk.surl.v2.core.generator.AbstractShortUrlGenerator;
import com.hk.surl.v2.core.strategy.hash.HashType;
import com.hk.surl.v2.entity.ShortURLExt;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : HK意境
 * @ClassName : MD5ShortUrlGenerator
 * @date : 2023/6/5 19:03
 * @description : Hash 算法，采用 MD5 进行hash，生成某个长度的 32 位的字符串
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Data
@Accessors(chain = true)
public class MD5ShortUrlGenerator extends AbstractShortUrlGenerator<ShortURLExt> {

    protected HashModeConfig hashModeConfig = new HashModeConfig();

    @Override
    public ShortURLExt preProcess() {
        return null;
    }


    /**
     * 此处需要携带长链接进入进行hash 生成
     * @param shortURLExt
     * @return
     */
    @Override
    public ShortURLExt doGenerate(ShortURLExt shortURLExt) {

        HashType hashMode = this.hashModeConfig.getType();

        // 原始长链接
        String longURI = shortURLExt.getLongURI();

        // hash 生成值
        String hash = hashMode.hash(longURI);


        // 长度控制: 32 * 4 = 128 bit, 32 bit / 4 = 8 字符
        boolean autoLengthControl = this.hashModeConfig.getAutoLengthControl() && hash.length() > longURI.length();
        if (autoLengthControl) {
            // 生成的长度比原始链接长度还长，需要进行控制：

        }

        // 设置短链接hash值
        this.shortUrl.setShortCode(hash).setShortURI(hash);

        return this.shortUrl;
    }


    /**
     * 后置处理，如果hash 冲突，则再后置处理进行循环再次hash ,使用 布隆过滤器进行判断
     * @return
     */
    @Override
    public ShortURLExt postProcess() {




        return null;
    }
}
