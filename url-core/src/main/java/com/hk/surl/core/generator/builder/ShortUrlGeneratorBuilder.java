package com.hk.surl.core.generator.builder;

import com.hk.surl.core.enums.strategy.*;
import com.hk.surl.core.generator.ShortUrlGenerator;
import com.hk.surl.core.provider.GenerateProvider;
import com.hk.surl.core.provider.random.RandomStringProvider;
import com.hk.surl.entity.ShortUrlExt;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class ShortUrlGeneratorBuilder  {

    // 短链接扩展实体
    protected ShortUrlExt urlExt ;

    // 选择的算法策略 : 默认使用 randomStringProvider
    protected GenerateProvider provider = new RandomStringProvider();

    // 是否开启缓存策略, 该缓存策略是指是否缓存在 Redis 里面, 如果有指定Short-URL过期策略的，则动态更具时间决定是否存放在缓存中
    protected Boolean enableCache = false;

    // 其他配置
    // 生成后的 短链接 URI 部分的长度
    protected Integer length = LengthStrategy.SMES.getLength();

    // 选择Short-URL 生成后的有效时间
    protected ExpirationStrategy expireStrategy= ExpirationStrategy.FOREVER ;

    // 是否选择使用线程安全的模式：这里准备弃用了
    protected SyncStrategy syncStrategy = SyncStrategy.DISABLE;

    // 是否需要对生成后的 短链接 再次进行加密-> 这里准备弃用了
    protected EncryptStrategy encryptStrategy=EncryptStrategy.NONE ;

    // 采用的压缩策略: 默认采用不压缩-> 这里准备弃用了
    protected CompressStrategy compressStrategy = CompressStrategy.NONE ;


    // 构造函数: 传入一个 ShortUrlExt
    public ShortUrlGeneratorBuilder(ShortUrlExt urlExt) {
        this.urlExt = urlExt;
    }

    // 传入一个 长链接 longUrl
    public ShortUrlGeneratorBuilder(String longUrl){
        this(new ShortUrlExt(longUrl));
    }




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
    public ShortUrlGenerator build(){
        ShortUrlGenerator generator = new ShortUrlGenerator();
        BeanUtils.copyProperties(this, generator);
        return generator ;
    }


}
