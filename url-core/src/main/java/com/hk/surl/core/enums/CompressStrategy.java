package com.hk.surl.core.enums;

/**
 * @author : HK意境
 * @ClassName : CompressStrategy
 * @date : 2022/4/18 21:07
 * @description : 压缩算法策略：
 *                      压缩算法整体上的效率不是很高，并且只有再字符串长度较长的情况下才能看到压缩效果，如果字符串长度较短则反而起不到压缩的效果
 * @Todo : gzip 字符串压缩，zlib 压缩字符串
 * @Bug : 这里使用 gzip 进行直接字符串的压缩可能会有乱码问题，所以建议使用 zlib 压缩
 * @Modified :
 * @Version : 1.0
 */
public enum CompressStrategy {

    // 不压缩
    NONE ,
    // zlib 压缩：支持对于数据流的压缩，对于字符串压缩建议采用这种压缩方式
    ZLIB,
    // gzip 压缩：gzip 一般用于文件的压缩，对于数据流，字符串等的压缩效果不是很好，只有再字符串长度很长的情况下才能看到压缩效果
    // GZip压缩 256字节以上才有压缩效果
    GZIP,
    // zlib 压缩：
    ;



}
