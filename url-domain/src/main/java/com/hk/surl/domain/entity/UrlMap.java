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
 * 长链接和短链接的映射关系
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_url_map")
@ApiModel(value="UrlMap对象", description="长链接和短链接的映射关系")
public class UrlMap implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "短链接ID号")
    private String shortId;

    @ApiModelProperty(value = "短链接url")
    private String shortUrl;

    @ApiModelProperty(value = "长链接ID")
    private String longId;

    @ApiModelProperty(value = "长链接url")
    private String longUrl;

    @ApiModelProperty(value = "长链接的MD5值")
    private String longMd;

    @ApiModelProperty(value = "映射关系是否可见")
    private Boolean visible;

    @ApiModelProperty(value = "映射关系创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "映射关系失效时间=创建时间+有效时长")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除:1删除，0未删除")
    private Boolean deleted;


}
