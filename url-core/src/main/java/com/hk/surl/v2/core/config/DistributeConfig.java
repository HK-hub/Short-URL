package com.hk.surl.v2.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : DistributeConfig
 * @date : 2023/6/7 20:05
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class DistributeConfig {

    /**
     * 是否开启安全模式：生成后的分布式id,因为单调递增，所以可能存在风险，如果开启安全模式，会对生成后的分布式id进行扰乱：
     *  1.转置
     *  2.加密
     *  3.转换为 16 进制
     */
    protected boolean safe = false;

    /**
     * 仅在开启安全模式的情况下起作用
     *  1.转置
     *  2.转换为 16 进制
     */
    protected int mode = 3;

}
