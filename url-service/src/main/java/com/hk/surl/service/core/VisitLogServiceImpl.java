package com.hk.surl.service.core;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IVisitLogService;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.VisitLogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/18 13:42
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl 短链接字符串
         * @param startTime 开始时间
         * @param endTime 结束时间
     * @return List
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public List<VisitLog> getListByDateTime(String shortUrl, LocalDateTime startTime, LocalDateTime endTime) {

        List<VisitLog> visitLogs = this.visitLogMapper.selectListByAccessTimeOrShortUrl(shortUrl, startTime, endTime);

        return visitLogs;
    }


    // 查询指定短链接访问数据
    @Override
    public ExcelWriter exportAccessDataToExcel(String shortUrl) {

        // 查询获取数据
        List<VisitLog> visitLogs = this.getListByShortUrl(shortUrl);

        // 内存操作，写到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id","编号");
        writer.addHeaderAlias("shortUrl","短链接");
        writer.addHeaderAlias("longUrl", "长链接");
        writer.addHeaderAlias("visitorIp","访问者IP地址");
        writer.addHeaderAlias("requestHost","请求主机");
        writer.addHeaderAlias("equipment","设备");
        writer.addHeaderAlias("visitorArea", "访问者位置");
        writer.addHeaderAlias("createTime", "访问时间");

        // 默认配置
        writer.write(visitLogs, true);

        return writer;
    }





}
