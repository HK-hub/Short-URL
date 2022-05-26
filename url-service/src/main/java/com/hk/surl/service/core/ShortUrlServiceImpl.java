package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IShortUrlService;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.domain.mapper.ShortUrlMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName : ShortUrlServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:26
 * @description :短链接表 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper, ShortUrl> implements IShortUrlService {

}
