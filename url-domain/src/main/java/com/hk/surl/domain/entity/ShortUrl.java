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
 * 短链接表
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_short_url")
@ApiModel(value="ShortUrl对象", description="短链接表")
public class ShortUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生成后的短链接")
    private String shortUrl;

    @ApiModelProperty(value = "短链接类型: http 请求链接，二维码，base64")
    private Integer type;

    @ApiModelProperty(value = "编号:目前作用未知")
    private String no;

    @ApiModelProperty(value = "可见性:1可见，0不可见")
    private Boolean visible;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "过期时间：表示短链接从创建经过到使用到消亡的时间，是指失效的时间： expiration_time=create_time+有效时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "跟新时间 ")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否可见：1可见，0不可见")
    private Boolean deleted;


}
