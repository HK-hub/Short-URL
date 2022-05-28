package com.hk.surl.core.generator;

import com.hk.surl.core.enums.CompressStrategy;
import com.hk.surl.core.enums.EncryptStrategy;
import com.hk.surl.core.enums.ExpirationStrategy;
import com.hk.surl.core.enums.SyncStrategy;
import com.hk.surl.core.provider.GenerateStrategy;
import com.hk.surl.core.strategy.encrypt.EncryptUrlStrategy;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.entity.ShortUrlExt;
import lombok.Data;

/**
 * @author : HK意境
 * @ClassName : Generator
 * @date : 2022/4/18 18:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class Generator implements IShortUrlGenerator {

    protected ShortUrlExt urlExt ;
    /*protected String longUrl ;
    protected String shortUrl ;*/

    // 选择的算法策略
    protected GenerateStrategy generateStrategy ;

    // 是否开启缓存策略, 该缓存策略是指是否缓存在 Redis 里面, 如果有指定Short-URL过期策略的，则动态更具时间决定是否存放在缓存中
    protected Boolean enableCache = false;

    // 选择Short-URL 生成后的有效时间
    protected ExpirationStrategy expireStrategy = ExpirationStrategy.NEVER;

    // 是否选择使用线程安全的模式
    protected SyncStrategy syncStrategy = SyncStrategy.DISABLE;

    // 其他配置
    // 生成后的 短链接 URI 部分的长度
    protected Integer length = 6;

    // 是否需要对生成后的 短链接 再次进行加密
    protected EncryptStrategy encryptStrategy=EncryptStrategy.NONE ;

    // 采用的压缩策略: 默认采用不压缩
    protected CompressStrategy compressStrategy = CompressStrategy.NONE ;



    @Override
    public ShortUrl generate() {
        ShortUrl shortURL = null ;

        // 线程安全配置
        if(SyncStrategy.SYNC == this.syncStrategy){
            shortURL = this.doGenerateWithSync();
        }else{
            shortURL = this.doGenerateWithoutSync();
        }
        // 加密策略进行加密
        String encryptString = EncryptUrlStrategy.encryptString(this);
        System.out.println(encryptString);
        return shortURL ;
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/4/18 16:44
     * @description : 线程安全策略
     * @Todo :
     * @params :
     * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    private synchronized ShortUrl  doGenerateWithSync(){
        ShortUrl shortURL = doGenerateWithoutSync();
        return shortURL ;
    }

    // 不启用线程安全策略
    private ShortUrl doGenerateWithoutSync(){
        String targetUrl = generateStrategy.provideShortUrl(this);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortUrl(targetUrl);
        return shortUrl;
    }

}
