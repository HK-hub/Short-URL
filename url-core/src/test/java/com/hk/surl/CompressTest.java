package com.hk.surl;

import com.hk.surl.core.common.CompressUtil;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

/**
 * @author : HK意境
 * @ClassName : CompressTest
 * @date : 2022/4/18 21:13
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CompressTest {

    @Test
    public void CompressStringTest() throws IOException {

        String url = "http://localhost:8080/cloud/45464uy6terju54grfea464uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders4hytejorders/user/15645juytkuy4648/dehtrehgvsfd114545glete";
        System.out.println("长链接长度："+url.length());
        byte[] bytes = CompressUtil.zlibCompress(url);
        //System.out.println(new String(bytes));
        System.out.println("------------------------------");
        System.out.println("压缩后字节数组长度："+bytes.length);

        StringBuilder sb = new StringBuilder();
        for (byte i : bytes) {
            sb.append(i);
        }

        System.out.println(sb.length());


        // 再压缩
        byte[] bytes1 = CompressUtil.zlibCompress(sb.toString());
        StringBuilder sb1 = new StringBuilder();
        for (byte b : bytes1) {
            sb1.append(b);
        }
        System.out.println(sb1.length());

        // 结论：再压缩效果不一定好，可能会出现压缩后字符串长度反而更长


    }


    // 路径压缩算法
    @Test
    public void pathCompressTest(){

        //String longUrl = "http://localhost:8080/cloud/45464uy6terju54grfea464uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders64uy6terju54grfea44hytejorders4hytejorders/user/15645juytkuy4648/dehtrehgvsfd114545glete";
        String longUrl = "https://blog.csdn.net/chuyouyinghe/article/details/122203309";
        System.out.println(longUrl.length());
        String shortUrl = CompressUtil.stringPathCompress(longUrl);
        double rate = (double) (longUrl.length()-shortUrl.length())/longUrl.length();
        System.out.println("压缩率:" + rate);
        System.out.println(shortUrl);

    }


    @Test
    public void gzipCompressTest(){

        String str = "teststring123aaaHIKHJK";

        String compress = compress(str);
        System.out.println(compress);

    }



    // gzip 压缩
    private static String compress(String str)
    {
        String str1 = null;
        ByteArrayOutputStream bos = null;
        try
        {
            bos = new ByteArrayOutputStream();
            BufferedOutputStream dest = null;

            byte b[] = str.getBytes();
            GZIPOutputStream gz = new GZIPOutputStream(bos,b.length);
            gz.write(b,0,b.length);
            bos.close();
            gz.close();

        }
        catch(Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        System.out.println(bos.toByteArray().length);
        System.out.println(Arrays.toString(bos.toByteArray()));

        byte[] bytes = bos.toByteArray();
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(aByte);
        }
        System.out.println(sb);
        return bos.toString();
    }





    // 使用 token 进行提交
    //git remote set-url origin https://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@github.com/HK-hub/Short-URL.git
}
