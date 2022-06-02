package com.hk.surl.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 长链接
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_long_url")
@ApiModel(value="LongUrl对象", description="长链接")
public class LongUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id 号")
    @TableId(type = IdType.ASSIGN_ID)
    private String id ;

    @ApiModelProperty(value = "完整的URL链接，可以是普通http 请求url ,qrcode ，base64等")
    private String url;

    @ApiModelProperty(value = "URI 统一资源标识符,url 最后一部分")
    private String uri;

    @ApiModelProperty(value = "url 资源类型: request 请求类型，二维码类型，base64类型")
    private Integer type;

    @ApiModelProperty(value = "调用协议类型:HHTP,RPC")
    private String protocol;

    @ApiModelProperty(value = "远端调用，长链接的请求协议版本")
    private String callerVersion;

    @ApiModelProperty(value = "长链接主机地址")
    private String host;

    @ApiModelProperty(value = "长链接的端口")
    private Integer port;

    @ApiModelProperty(value = "长链接的请求端口")
    private String method;

    @ApiModelProperty(value = "url 链接地址请求参数，? 分割")
    private String params;

    @ApiModelProperty(value = "是否可见1可见，0不可见")
    private Boolean visible;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除:0未删除，1已删除 ")
    @TableLogic
    private Boolean deleted;



}
