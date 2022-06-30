package com.hk.surl.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.VisitLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 短链接访问日志 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface VisitLogMapper extends BaseMapper<VisitLog> {


    // 查询指定短链接的访问数据
    @Select("select * from tb_visit_log where short_url = #{shortUrl} order by create_time desc")
    List<VisitLog> selectListByShortUrl(@Param(value = "shortUrl") String shortUrl);

    // 查询指定日期内的访问数据
    List<VisitLog> selectListByAccessTimeOrShortUrl(@Param(value = "shortUrl")String shortUrl ,
                                                    @Param(value = "startTime")LocalDateTime startTime ,
                                                    @Param(value = "endTime")LocalDateTime endTime);

    // 指定时间内新增的访问用户
    // 查询指定时间内的访问记录，投影取出ip 地址, 判断不属于指定时间以前的ip 地址
    List<VisitLog> getNewVisitDataByDatetime(@Param("shortUrl") String shortUrl,
                                                 @Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);

    // 查询指定短链接创建以来的每天的访问数据
    List<Map<String, Object>> selectListEveryDayAccessData(String shortUrl);
}
