package com.hk.surl.common.log;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author : HK意境
 * @ClassName : LogThreadLocal
 * @date : 2022/6/14 9:57
 * @description : 存放请求线程资源
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LogThreadLocal {

    // threadlocal 日志缓存
    private static final ThreadLocal<Map<String , Object>> LOG_CACHE = new ThreadLocal<>();

    // 清除缓存，清除数据
    public void clear(){
        LOG_CACHE.remove();
    }


    // 添加
    public static void setAttribute(String key, Object value) {
        Map<String, Object> cache = LogThreadLocal.getCache();
        cache.put(key, value);
    }

    // 获取数据
    public static Object getAttribute(String key) {
        Map<String, Object> cache = getCache();
        return cache.get(key);
    }


    // 获取字符串数据
    public static String getAttributeStr(String key) {
        Map<String, Object> cache = getCache();
        Object value = cache.get(key);
        return value == null ? null : String.valueOf(value);
    }

    // 获取缓存 map 数据
    public static Map<String, Object> getCache() {
        Map<String, Object> cache = LOG_CACHE.get();
        if (cache == null) {
            cache = new HashMap<>();
            LOG_CACHE.set(cache);
        }
        return cache;
    }

}
