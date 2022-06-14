package com.hk.surl.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hk.surl.common.util.TranceIdUtil;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统调用链路日志
 * @TableName tb_log_trance
 */
@ToString
@Data
@TableName(value ="tb_log_trance")
public class LogTrance implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    /**
     * 链路追踪id
     */
    @TableId(value = "trance_id")
    private String tranceId = TranceIdUtil.getTraceId();

    /**
     * 业务类型
     */
    @TableField(value = "business_type")
    private String businessType;

    @TableField(value = "business_method")
    private String businessMethod ;

    /**
     *  业务日志级别
     *   0 紧急, 1 重要, 2普通
     */
    @TableField(value = "level")
    private String level;

    /**
     * 请求路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * ipv4 的客户端ip 地址
     */
    @TableField(value = "ip_address")
    private String ipAddress;

    /**
     * 客户端浏览器，操作系统
     */
    @TableField(value = "user_agent")
    private String userAgent;

    /**
     * 客户端地址：国家，省份，城市
     */
    @TableField(value = "location")
    private String location;

    /**
     * 请求参数
     */
    @TableField(value = "parameters")
    private String parameters;

    /**
     * 请求方法
     */
    @TableField(value = "request_method")
    private String requestMethod;

    /**
     * 执行的操作
     */
    @TableField(value = "operate")
    private String operate;

    /**
     * 操作用户
     */
    @TableField(value = "operator")
    private String operator;

    /**
     * 响应结果
     */
    @TableField(value = "result")
    private String result;

    /**
     * 响应状态码
     */
    @TableField(value = "code")
    private Integer code;

    /**
     * 请求备注，出错原因
     */
    @TableField(value = "msg")
    private String msg;

    /**
     * 请求创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 请求调用链路执行时间
     */
    @TableField(value = "execute_time")
    private String executeTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getCreateTime() {
        return createTime;
    }


    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getEndTime() {
        return endTime;
    }
}