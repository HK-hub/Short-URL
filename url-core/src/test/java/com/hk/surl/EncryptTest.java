package com.hk.surl;

import com.hk.surl.core.common.EncryptUtil;
import org.junit.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : EncryptTest
 * @date : 2022/4/18 20:52
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class EncryptTest {

    @Test
    public void encryptUrl(){

        String url = "https://www.enjoytrader89.com/?jskey=%1Cr%86%3C%CC%E8%DF%045%5B%D2H%86%963t%FA%0E%9F%CF%F1a%DD%01%A3uw%9Es%BAi%94J%1Ce%D6%CC%FCQ%DF%03#/";

        ///System.out.println(EncryptUtil.MD5(url));
        String encode = URLEncoder.encode(url, StandardCharsets.UTF_8);
        System.out.println(encode);

    }




}
