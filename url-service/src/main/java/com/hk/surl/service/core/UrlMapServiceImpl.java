package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.UrlMap;
import com.hk.surl.domain.mapper.LongUrlMapper;
import com.hk.surl.domain.mapper.UrlMapMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName : UrlMapServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:26
 * @description : 长链接和短链接的映射关系 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class UrlMapServiceImpl extends ServiceImpl<UrlMapMapper, UrlMap> implements IUrlMapService {

    @Autowired
    private UrlMapMapper urlMapMapper ;
    @Autowired
    private LongUrlMapper longUrlMapper ;

    /**
     * @methodName : getLongUrlListByShortUrl
     * @author : HK意境
     * @date : 2022/6/10 21:04
     * @description :
     * @Todo : 首先获取到 对应的长链接 集合的 id 值列表，然后进行批量查询
     * @apiNote : 通过 短链接字符串查询 对应的长链接集合
     * @params :
         * @param shortUrl 短链接字符串
     * @return List<LongUrl>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public List<LongUrl> getLongUrlListByShortUrl(String shortUrl) {

        // 查询 短链接对应的长链接对象id 值
        LambdaQueryChainWrapper<UrlMap> wrapper = new LambdaQueryChainWrapper<>(urlMapMapper);
        wrapper.eq(UrlMap::getShortUrl, shortUrl);
        // 查询 得到集合
        List<UrlMap> urlMaps = urlMapMapper.selectList(wrapper);
        // 收集出 id 列表
        List<String> ids = urlMaps.stream().map(UrlMap::getLongId).collect(Collectors.toList());
        // 批量查询 得到 长链接对象
        List<LongUrl> longUrls = longUrlMapper.selectListByIds(ids);

        return longUrls;
    }





}
