package com.hk.surl.core.strategy;

import com.hk.surl.core.strategy.enums.CompressStrategy;
import com.hk.surl.core.strategy.enums.EncryptStrategy;
import com.hk.surl.core.strategy.enums.ExpirationStrategy;
import com.hk.surl.core.strategy.enums.SyncStrategy;
import com.hk.surl.entity.ShortURL;
import com.hk.surl.entity.ShortUrlExt;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @author : HK意境
 * @ClassName : ShortUrlGeneratorBuilder
 * @date : 2022/4/14 9:58
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(fluent = true)
public class ShortUrlGeneratorBuilder  {

    protected ShortUrlExt urlExt ;
    /*protected String longUrl ;
    protected String shortUrl ;*/

    // 选择的算法策略
    protected GenerateStrategy generateStrategy ;

    // 是否开启缓存策略, 该缓存策略是指是否缓存在 Redis 里面, 如果有指定Short-URL过期策略的，则动态更具时间决定是否存放在缓存中
    protected Boolean enableCache = false;

    // 选择Short-URL 生成后的有效时间
    protected ExpirationStrategy expireStrategy= ExpirationStrategy.NEVER ;

    // 是否选择使用线程安全的模式
    protected SyncStrategy syncStrategy =SyncStrategy.DISABLE;

    // 其他配置
    // 生成后的 短链接 URI 部分的长度
    protected Integer length = 6;

    // 是否需要对生成后的 url 进行加密
    protected EncryptStrategy encryptStrategy =EncryptStrategy.MD5;

    // 采用的压缩策略: 默认采用不压缩
    protected CompressStrategy compressStrategy = CompressStrategy.NONE ;


    /**
     * @methodName : build()
     * @author : HK意境
     * @date : 2022/4/18 18:55
     * @description : 根据参数构建Generator 类
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    public Generator build(){
        Generator generator = new Generator();
        BeanUtils.copyProperties(this,generator);
        return generator ;
    }


}
