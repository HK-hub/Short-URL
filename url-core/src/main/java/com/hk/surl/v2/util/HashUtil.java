package com.hk.surl.v2.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;


/**
 * @author : HK意境
 * @ClassName : HashUtil
 * @date : 2023/6/5 19:11
 * @description : Hash 算法实现工具
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class HashUtil {


    /**
     * 进行md5摘要
     * @param plain
     * @return
     */
    public static String md5(String plain) {

        HashFunction hashFunction = Hashing.md5();
        String md5 = hashFunction.hashString(plain, StandardCharsets.UTF_8).toString();

        return md5;
    }


    /**
     * 进行 MurmurHash 算法生成 32 位
     * @param plain
     * @return
     */
    public static String murmurHash(String plain) {

        String hash = Hashing.murmur3_32_fixed()
                .hashString(plain, StandardCharsets.UTF_8)
                .toString();

        return hash;
    }




}
