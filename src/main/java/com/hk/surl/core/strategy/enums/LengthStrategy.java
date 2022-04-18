package com.hk.surl.core.strategy.enums;

import lombok.Getter;

/**
 * @author : HK意境
 * @ClassName : LengthStrategy
 * @date : 2022/4/18 13:48
 * @description : 小型企业选择4-6 个长度即可，中大型企业应该选择10 个字符及以上的，也可以选择进行自定义操作
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Getter
public enum LengthStrategy {

    // 中小型企业：建议4-6 个长度即可：当使用 62 进制的字符来进行选择时候可能有：44,261,653,680 种组合情况
    SMES(6),

    // 大型企业：建议选择 10 个长度以上，
    LARGE_ENTERPRISE(10);


    private Integer length ;

    // 自定义长度
    LengthStrategy(Integer length) {
        this.length = length;
    }
}

