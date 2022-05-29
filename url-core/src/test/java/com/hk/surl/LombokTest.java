package com.hk.surl;

import com.hk.surl.domain.entity.ShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author : HK意境
 * @ClassName : LombokTest
 * @date : 2022/4/13 21:23
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j(topic = "LombokTest")
public class LombokTest {

    // 测试 lombok 的可用性和稳定性
    @Test
    public void getterAndSetterTest(){

        System.out.println("test lombok");
        ShortUrl shortURL = new ShortUrl("http://localhost:8080/123456-fioggreo");
        System.out.println(shortURL.getShortUrl()+","+shortURL.getShortUrl());
        log.info("url:{}, shortUrl:{}",shortURL.getShortUrl(),shortURL.getShortUrl());

    }



}
