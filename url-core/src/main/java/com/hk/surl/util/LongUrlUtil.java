package com.hk.surl.util;

import com.hk.surl.domain.entity.LongUrl;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : HK意境
 * @ClassName : LongUrlUtil
 * @date : 2022/6/11 13:52
 * @description : 长链接生成工具,
 * @Todo :通过给定 长链接字符串，解析出各个参数配置，返回长链接对象
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LongUrlUtil {

    /**
     * @methodName : analyzeStringToLongUrl
     * @author : HK意境
     * @date : 2022/6/11 13:54
     * @description : 解析长链接字符串生成长链接对象
     * @Todo :
     * @apiNote :
     * @params :
     * @return LongUrl
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public static LongUrl analyzeStringToLongUrl(String longUrlStr){

        LongUrl longUrl = null;

        try {
            // 构造 url 对象: 可以兼容多种协议，查询参数
            URL url = new URL(longUrlStr);
            longUrl = new LongUrl(longUrlStr,url);

        } catch (MalformedURLException e) {
            return null ;
        }

        return longUrl;
    }


}
