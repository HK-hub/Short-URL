package com.hk.surl.common.log;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : LogTrance
 * @date : 2022/6/14 9:38
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@TableName("tb_log_trance")
@Data
@ToString
public class LogTrance {

    // 链路ID
    private String tranceId ;

    // 业务类型
    private String businessType ;

    // 业务方法
    private String businessMethod ;

    // 业务日志级别
    // 0 紧急, 1 重要, 2普通
    private String level ;

    // 请求路径
    private String path ;

    // 请求者 ip
    private String ipAddress;

    // 请求浏览器
    private String userAgent ;

    // 请求者地址
    private String location ;

    // 请求参数
    private String parameters ;

    // 请求方法
    private String requestMethod ;

    // 请求的操作
    private String operate ;

    // 操作者
    private String operator ;

    // 响应结果
    private String result ;

    // 响应结果状态
    private Integer code ;

    // 请求备注
    private String msg ;

    // 请求时间
    private LocalDateTime createTime ;

    // 执行时间
    private String executeTime ;

    // 结束时间
    private LocalDateTime endTime ;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public LocalDateTime getCreateTime() {
        return createTime;
    }


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public LocalDateTime getEndTime() {
        return this.endTime;
    }


}
