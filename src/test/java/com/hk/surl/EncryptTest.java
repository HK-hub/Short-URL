package com.hk.surl;

import com.hk.surl.core.common.EncryptUtil;
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

        String url = "http://localhost:8080/cloud/45464uy6terju54grfea44hytejorders/user/15645juytkuy4648/dehtrehgvsfd114545glete";

        System.out.println(EncryptUtil.MD5(url));


    }




}
