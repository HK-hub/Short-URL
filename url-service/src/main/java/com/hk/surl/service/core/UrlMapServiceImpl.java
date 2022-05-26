package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.domain.entity.UrlMap;
import com.hk.surl.domain.mapper.UrlMapMapper;
import org.springframework.stereotype.Service;

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

}
