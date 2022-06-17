package com.hk.surl.api.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.VisitLog;

import java.util.List;

/**
 * @ClassName : IVisitLogService
 * @author : HK意境
 * @date : 2022/5/26
 * @description :短链接访问日志 服务类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface IVisitLogService extends IService<VisitLog> {

    // 获取指定短链接的访问日志
    List<VisitLog> getListByShortUrl(String shortUrl);


}
