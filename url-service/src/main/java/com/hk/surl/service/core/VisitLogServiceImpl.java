package com.hk.surl.service.core;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IVisitLogService;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.VisitLogMapper;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.OrderedMapIterator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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


    /**
     * @methodName :exportAccessDataToExcel
     * @author : HK意境
     * @date : 2022/6/25 13:11
     * @description :查询指定短链接访问数据
     * @Todo :
     * @apiNote 导出指定短链接的访问数据
     * @params :
         * @param shortUrl
     * @return ExcelWriter
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
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
        writer.addHeaderAlias("targetHost", "目标主机");
        writer.addHeaderAlias("referrer", "访问来源");
        writer.addHeaderAlias("equipment","设备");
        writer.addHeaderAlias("visitorArea", "访问者位置");
        writer.addHeaderAlias("createTime", "访问时间");

        // 默认配置
        writer.write(visitLogs, true);

        return writer;
    }



    /**
     * @methodName : getAloneTotalVisitorIp
     * @author : HK意境
     * @date : 2022/6/25 13:12
     * @description : 查询指定短链接访问数据中的全部独立IP
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl
     * @return List<Map.Entry<String, Integer>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public Map<String, Integer> getAloneTotalVisitorIp(String shortUrl) {
        // 查询指定短链接访问数据
        List<VisitLog> visitLogs = this.visitLogMapper.selectListByShortUrl(shortUrl);

        // 使用Map 统计去重
        Map<String, Integer> aloneIp = new HashMap<>();
        visitLogs.forEach(visitLog -> {

            if (aloneIp.containsKey(visitLog.getVisitorIp())){
                // 包含了访问IP，进行更新
                aloneIp.replace(visitLog.getVisitorIp(),aloneIp.get(visitLog.getVisitorIp())+1);

            }else{
                // 不包含访问IP
                aloneIp.put(visitLog.getVisitorIp(), 1);
            }
        });


        // 排序 按照访问次数进行排序
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        aloneIp.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

        return aloneIp;
    }


    /**
     * @methodName : getEveryDayAccessData
     * @author : HK意境
     * @date : 2022/6/26 14:49
     * @description : 获取短链接的每日的访问数据
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl 指定短链接
     * @return Map<String, List<VisitLog>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public List<Map<String, Object>> getEveryDayAccessData(String shortUrl) {

        // 查询获取每天访问数据和访独立用户
        List<Map<String, Object>> everyDayDataMap =  this.visitLogMapper.selectListEveryDayAccessData(shortUrl);

        return everyDayDataMap;
    }


}
