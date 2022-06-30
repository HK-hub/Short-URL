package com.hk.surl.api.core;

import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.VisitLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 获取指定时间内的访问数据
    List<VisitLog> getListByDateTime(String shortUrl , LocalDateTime startTime, LocalDateTime endTime);


    // 获取指定短链接的访问数据：excel 文件数据
    // 导出指点短链接访问数据到 excel 文件种
    ExcelWriter exportAccessDataToExcel(String shortUrl);

    // 获取短链接所有的独立访问IP
    Map<String,Integer> getAloneTotalVisitorIp(String shortUrl);

    // 获取每天的访问数据
    List<Map<String, Object>> getEveryDayAccessData(String shortUrl);
}
