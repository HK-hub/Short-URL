package com.hk.surl.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短链接访问日志
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_visit_log")
@ApiModel(value="VisitLog对象", description="短链接访问日志")
public class VisitLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "短链接ID")
    private String shortUrlId;

    @ApiModelProperty(value = "长链接ID")
    private String longUrlId;

    @ApiModelProperty(value = "访问者的IP地址")
    private String visitorIp;

    @ApiModelProperty(value = "请求的域名")
    private String requestHost;

    @ApiModelProperty(value = "长链接(目标链接)域名")
    private String targetHost;

    @ApiModelProperty(value = "访问设备:操作系统类型，浏览器类型")
    private String equipment;

    @ApiModelProperty(value = "访问地区：国家，省份，市级，区级")
    private String visitorArea;

    @ApiModelProperty(value = "访问方法")
    private String method;

    @ApiModelProperty(value = "访问时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "跟新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除：1删除，0未删除")
    private Boolean deleted;


}
