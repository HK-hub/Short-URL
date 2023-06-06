package com.hk.surl.v2.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : RandomConfig
 * @date : 2023/6/6 22:04
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class RandomConfig {

    /**
     * 随机字符串池
     */
    protected char[] pooling = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-=+".toCharArray();


    /**
     * 快速随机模式：一次生成全部位置的随机字符
     */
    protected boolean fast = false;

    /**
     * 使用线程安全的随机
     */
    protected boolean threadSafe = true;

}
