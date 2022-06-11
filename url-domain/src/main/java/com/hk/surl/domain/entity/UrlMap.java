package com.hk.surl.domain.entity;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.Security;
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
@NoArgsConstructor
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "映射关系失效时间=创建时间+有效时长")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除:1删除，0未删除")
    @TableLogic
    private Boolean deleted;


    // 根据 短链接对象和长链接对象创建映射对象
    public UrlMap(ShortUrl shortUrl, LongUrl longUrl) {

        this.setShortId(shortUrl.getId());
        this.setShortUrl(shortUrl.getShortUrl());
        this.setLongId(longUrl.getId());
        this.setLongUrl(longUrl.getUrl());
        this.setLongMd(SecureUtil.md5(this.longUrl));
    }
}
