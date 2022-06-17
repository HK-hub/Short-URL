package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IVisitLogService;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.VisitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : VisitLogServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:26
 * @description : 短链接访问日志 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class VisitLogServiceImpl extends ServiceImpl<VisitLogMapper, VisitLog> implements IVisitLogService {

    @Resource
    private VisitLogMapper visitLogMapper ;


    // 查询指定短链接的访问数据
    @Override
    public List<VisitLog> getListByShortUrl(String shortUrl) {

        List<VisitLog> visitLogs = visitLogMapper.selectListByShortUrl(shortUrl);

        return visitLogs;
    }
}
