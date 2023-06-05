package com.hk.surl.v2.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : RecyclingModeConfig
 * @date : 2023/6/4 15:18
 * @description : 进制算法配置: 73进制
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class RecyclingModeConfig {

    /**
     * 字符串池
     */
    private char[] pooling = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_#$*()+=@!~".toCharArray();


    /**
     * 初始化的计数: 默认 1111111, 能够生成4位的code
     */
    private Long initNumber = 10000000L;

}
