package com.hk.surl.common.util;

import java.util.UUID;

/**
 * @ClassName : TranceIdUtil
 * @author : HK意境
 * @date : 2022/6/14 9:24
 * @description : 链路id  生成器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TranceIdUtil {

    /**
     * 生成traceId
     *
     * @return TraceId 基于UUID
     */
    public static String getTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
