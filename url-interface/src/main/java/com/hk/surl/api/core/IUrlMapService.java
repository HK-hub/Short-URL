package com.hk.surl.api.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.UrlMap;

import java.util.List;

/**
 * @ClassName : IUrlMapService
 * @author : HK意境
 * @date : 2022/5/26
 * @description :长链接和短链接的映射关系 服务类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface IUrlMapService extends IService<UrlMap> {

    // 根据 短链接字符串 查询对应的长链接对象集合
    List<LongUrl> getLongUrlListByShortUrl(String shortUrl);

    // 根据 短链接id值 查询对应的长链接对象集合
    List<LongUrl> getLongUrlListBySId(String sid);

    // 根据长连接 id 值查询对应的urlmap 映射对象
    List<UrlMap> getUrlMapByLongId(String lid);

    // 根据 长链接字符串查询映射对象
    UrlMap getUrlMapByLongUrl(String longUrl);

}
