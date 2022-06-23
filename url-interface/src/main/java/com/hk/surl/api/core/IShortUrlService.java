package com.hk.surl.api.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.entity.ShortUrl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName : IShortUrlService
 * @author : HK意境
 * @date : 2022/5/26
 * @description :短链接表 服务类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface IShortUrlService extends IService<ShortUrl> {


    // 根据 长链接字符串 新增 短链接对象，长链接对象，映射对象
    Map.Entry<ShortUrl, AnonymousUser> newShortUrl(String longUrl, LocalDateTime expirationTime) throws ExecutionException, InterruptedException;

    // 批量创建短链接对象
    List<ShortUrl> batchNewShortUrl(List<String> longUrls, LocalDateTime expirationTime) throws ExecutionException, InterruptedException;

    // 根据 id 删除 短链接对象，映射对象，长链接对象
    boolean removeShortUrlById(String id);

    // 删除指定短链接对象，连带删除长链接对象，映射对象
    Boolean removeShortUrl(ShortUrl shortUrl);

    // 删除指定短链接字符串的短链接对象
    Boolean removeShortUrlByUrl(String shortUrlStr);


}
