package com.hk.surl.api.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.ShortUrl;

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
    ShortUrl newShortUrl(String longUrl);

}
