package com.hk.surl.core.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.*;

/**
 * @author : HK意境
 * @ClassName : CompressUtil
 * @date : 2022/4/18 21:10
 * @description : 字符串压缩工具类：gzip 压缩算法，zlib 压缩算法
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CompressUtil {

    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(CompressUtil.class);


    /**
     * 使用gzip进行压缩
     */
    public static String gzipCompress(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return Base64.encodeBase64String(out.toByteArray());
    }



    /**
     * 使用gzip进行解压缩
     */
    public static String gzipUncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = Base64.decodeBase64(compressedStr);
            //compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            try {
                out.close();
            } catch (IOException e) {
            }
        }
        return decompressed;
    }


    /**
     * 压缩字符串.
     * 使用 UTF8 字符集解码
     *
     * @param str : 待压缩数据 （UTF-8编码）
     *
     * @return byte[] 压缩后的数据
     */
    public static byte[] zlibCompress(String str) {
        if (str == null || "".equals(str)){
            return null;
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return doZipCompress(bytes);
    }


    /**
     * 压缩
     *
     * @param data
     *            待压缩数据
     * @return byte[] 压缩后的数据
     */
    protected static byte[] doZipCompress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length)) {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            LOGGER.error("compress: ",e );
        }finally {
            compresser.end();
        }
        return output;
    }


    /**
     * 解压缩. (采用UTF-8编码)
     *
     * @param data
     *            待压缩的数据
     * @return String 解压缩后的字符串
     */
    public static String zlibDecompressStr(byte[] data) {
        byte[] decompress = doZipDecompress(data);
        return new String(decompress,StandardCharsets.UTF_8);
    }

    /**
     * 解压缩
     *
     * @param data
     *            待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    protected static byte[] doZipDecompress(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data);

        try(ByteArrayOutputStream o = new ByteArrayOutputStream(data.length)) {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            LOGGER.error("compress: ",e );
        } finally {
            decompresser.end();
        }

        return output;
    }



    /**
     * @methodName :stringPathCompress
     * @author : HK意境
     * @date : 2022/5/29 19:32
     * @description : 字符串路径压缩算法
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug : 第一版：字符串存入 map 的时候是有序的存储的，不太合理，容易出现冲突
     * @Modified :
     * @Version : 1.0.0
     */
    public static String stringPathCompress(String longUrl){

        Map<Character, Integer> charMap = new LinkedHashMap<>();

        for (char c : longUrl.toCharArray()) {

            if (Character.isLetter(c)){
                if (charMap.containsKey(c)){
                    charMap.put(c,charMap.get(c)+1);
                }else{
                    charMap.put(c,1);
                }
            }
        }

        StringBuilder url = new StringBuilder();

        // 第二版：在第一版的基础上进行首尾计算
        // 分治合并相邻的, 然后对出现次数取模拼接
        List<Character> keys = new ArrayList<>(charMap.keySet());
        System.out.println(keys.size());
        for (int i = 0, j = keys.size()-1; i < j; i++,j--) {
            int count = (charMap.get(keys.get(i))+charMap.get(keys.get(j)))%10;
            url.append(keys.get(i)).append(keys.get(j))
                    .append(count);
        }


        // 第一版：如果字符串字母数量种类，较多，则最后的短链接长度可能就会较长 26 个字母-> 52 个长度
        /*charMap.forEach((k,v) -> {
            url.append(k).append(v);
        });*/

        return url.toString();
    }



}
