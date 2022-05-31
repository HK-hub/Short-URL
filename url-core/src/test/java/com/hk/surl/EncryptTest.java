package com.hk.surl;

import com.hk.surl.core.common.util.EncryptUtil;
import org.junit.Test;

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
        String url2 = "https://blog.csdn.net/lilizhou2008/article/details/107625083" +
                "?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-107625083-blog-114280025.pc_relevant_paycolumn_v3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-107625083-blog-114280025.pc_relevant_paycolumn_v3&utm_relevant_index=2";
        System.out.println(EncryptUtil.MD5(url));



        System.out.println(EncryptUtil.SHA1(url2));

    }




}
