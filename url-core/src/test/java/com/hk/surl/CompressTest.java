package com.hk.surl;

import com.hk.surl.core.common.CompressUtil;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
        System.out.println(url.length());
        System.out.println(CompressUtil.compress(url).length());

    }


}
