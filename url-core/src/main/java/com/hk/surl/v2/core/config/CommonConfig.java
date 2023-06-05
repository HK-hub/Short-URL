package com.hk.surl.v2.core.config;

import com.hk.surl.core.enums.strategy.LengthStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : CommonConfig
 * @date : 2023/6/3 23:38
 * @description : 通用配置
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class CommonConfig {

    /**
     * 生成后短链接的code 长度(除去协议，域名后的部分), 默认6
     * 此生产长度只是尽可能的满足，可能会因为选择的生成算法等原因限制而无法满足，例如你选择了分布式id, 发号器等算法
     */
    protected Integer length = LengthStrategy.SMES.getLength();

    /**
     * 默认开启缓存
     */
    protected Boolean enableCache = Boolean.TRUE;

    /**
     * 当采用不安全的生成算法进行短链接生产的时候是否需要对生产的短链接进行二次加密操作
     */
    protected Boolean enableEncrypt = Boolean.FALSE;


    /**
     * 是否开启池化：预先生产一些短链接集合，然后后续直接从缓存获取短链接，异步补充，回收短码即可
     */
    protected Boolean enablePooling = Boolean.TRUE;




}
