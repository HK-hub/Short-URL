package com.hk.surl.core.provider.compress;

import com.hk.surl.core.common.CompressUtil;
import com.hk.surl.core.provider.GenerateStrategy;
import com.hk.surl.core.generator.Generator;
import com.hk.surl.core.enums.CompressStrategy;

import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : CompressProvider
 * @date : 2022/4/18 21:01
 * @description : 使用压缩算法：这里仅仅提供常见的字符串压缩算法
 *                  - gzip 算法
 *                  -
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CompressProvider implements GenerateStrategy {

    // 使用 压缩工具类进行压缩数据

    @Override
    public String provideShortUrl(Generator generator) {

        // 获取长链接字符串
        String longUrl = generator.getUrlExt().getLongUrl();

        // 压缩后的数据缓冲buffer
        String shortUrl;

        // 获取压缩策略
        CompressStrategy compressStrategy = generator.getCompressStrategy();

        // 更具选择的压缩算法来执行具体的压缩行为
        switch (compressStrategy){

            case NONE:
                // 不压缩
                shortUrl = longUrl ;
                break;
            case PATH:
                shortUrl = CompressUtil.stringPathCompress(longUrl);
                break;
            case ZLIB:
                // 使用 zlib 压缩
                shortUrl = new String(CompressUtil.zlibCompress(longUrl), StandardCharsets.UTF_8);
                break;
            case GZIP:
                shortUrl = CompressUtil.gzipCompress(longUrl);
                break;
            default:
                // 默认不采用压缩方式
                shortUrl = longUrl ;
                break;
        }

        // 返回压缩后的短链接
        return shortUrl ;
    }
}
