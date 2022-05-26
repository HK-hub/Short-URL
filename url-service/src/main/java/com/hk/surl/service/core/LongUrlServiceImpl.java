package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.ILongUrlService;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.mapper.LongUrlMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName : LongUrlServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:25
 * @description : 长链接 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class LongUrlServiceImpl extends ServiceImpl<LongUrlMapper, LongUrl> implements ILongUrlService {

}
