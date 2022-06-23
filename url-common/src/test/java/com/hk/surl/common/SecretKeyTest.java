package com.hk.surl.common;

import com.hk.surl.common.util.SecretKeyUtil;
import org.junit.Test;


/**
 * @author : HK意境
 * @ClassName : SecretKeyTest
 * @date : 2022/6/22 0:17
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class SecretKeyTest {

    @Test
    public void secretKeyGenerateTest(){

        String secretKey = SecretKeyUtil.generateSecretKey("su.io/@eV_Tt", "");

        System.out.println(secretKey);
    }

}
