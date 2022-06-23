package com.hk.surl.common.util;

import cn.hutool.crypto.digest.DigestUtil;

import java.util.Random;

/**
 * @author : HK意境
 * @ClassName : SecretKeyUtil
 * @date : 2022/6/22 0:00
 * @description : 生产 secretKey 工具类
 * @Todo : 生产算法：讲 shortUrl , longUrl 作为payload , 以时间戳为随机盐
 * @Bug : longUrl 过长不适合作为payload 负载，应该优化为使用 短链接+动态长度的随机盐
 * @Modified :
 * @Version : 1.0
 */
public class SecretKeyUtil {

    private static final String salt = "zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP1234567890,.<>:?";
    private static final int saltLength = 16;

    /***
     * 使用随机盐与短链接进行 md5 加密
     * @param shortUrl
     * @param longUrl
     * @return
     */
    public static String generateSecretKey(String shortUrl , String longUrl){

        // 获取随机盐
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        //循环16次，共取出16个随机字符
        for (int i = 0; i < 16; i++) {
            //每次生成一个67以内的随机数
            int number = random.nextInt(68);
            //生成的随机数作为 str 字符串的下标；从 str 中取出随机字符后追加到 stringBuffer
            stringBuffer.append(salt.charAt(number));
        }

        // 通过 短链接+随机盐进行md5 加密
        String secretKey = DigestUtil.md5Hex(shortUrl + stringBuffer.toString());

        return secretKey ;
    }



}
