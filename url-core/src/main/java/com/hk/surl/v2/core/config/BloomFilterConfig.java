package com.hk.surl.v2.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : BloomFilterConfig
 * @date : 2023/6/6 20:10
 * @description : 布隆过滤器配置
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class BloomFilterConfig {

    /**
     * 预估数据量: 100 万。一天 10000 短链接生成，可以使用 100 天，> 3 个月，缓存一个月即可
     */
    private int insertions = 10000 * 100;


    /**
     * 判重错误率：一万条里面出现 1 条
     */
    private double fpp = 0.0001;


}
