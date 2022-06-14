package com.hk.surl.common;

import cn.hutool.core.io.resource.ClassPathResource;
import com.hk.surl.common.entity.Location;
import com.hk.surl.common.util.IPUtil;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author : HK意境
 * @ClassName : IPUtilTest
 * @date : 2022/6/14 14:42
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class IPUtilTest {


    @Test
    public void fileReadTest(){

        File file =  new File("url-common/src/main/resources/ip2region.db");
        if (file == null) {
            System.out.println("file is null");
        }else{

            System.out.println("file is not null");
            System.out.println(file.getName());
            System.out.println(file.getAbsolutePath());

        }

    }


    @Test
    public void getCityInfoTest() throws IOException, InvocationTargetException, IllegalAccessException {

        InputStream stream = new FileInputStream("src/main/resources/GeoLite2-City.mmdb");

        if (stream != null) {
            System.out.println("stream is not nll");
        }else{
            System.out.println("stram is null");
        }

        String ipAddressLocation = IPUtil.getCityInfo("127.0.0.1");

    }


    @Test
    public void ipAddressTest() throws IOException, InvocationTargetException, IllegalAccessException {



        //IPUtil.getIpAddress()
        //System.out.println(ipAddressLocation);

    }


}
