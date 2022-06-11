package com.hk.surl.web.test;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : HK意境
 * @ClassName : UrlTest
 * @date : 2022/6/11 14:12
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class UrlTest {

    @Test
    public void URLTest() throws IOException {

        String longUrl = "https://blog.csdn.net/weixin_34221374/article/details/114346117?id=greghtrhtl&ur=grghh&pw=grh424515";

        URL url = new URL(longUrl);
        System.out.println("getContent:" +url.getContent());
        System.out.println("host:"+url.getHost());
        System.out.println("path:" + url.getPath());
        System.out.println("auth:"+url.getAuthority());
        System.out.println("query:"+url.getQuery());
        System.out.println("userinfo:"+ url.getUserInfo());
    }


}
