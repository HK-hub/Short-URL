package com.hk.surl.web.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author : HK意境
 * @ClassName : StringTest
 * @date : 2022/6/9 20:40
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
public class StringTest {

    @Test
    public void subStringTest(){

        String url = "http://localhost:8080/abc/agrg?greh=gteh?ferg=54";
        url = url.substring(0,url.indexOf("?"));

        log.info("{}",url);

    }




}
