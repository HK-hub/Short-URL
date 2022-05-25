package com.hk.surl.domain.dbo.core;

/**
 * @author : HK意境
 * @ClassName : ShortUrlExt
 * @date : 2022/4/14 9:53
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ShortUrlExt extends ShortURL{

    public ShortUrlExt(String longUrl){
        this.longUrl = longUrl ;
    }

    public ShortUrlExt() {
    }
}
