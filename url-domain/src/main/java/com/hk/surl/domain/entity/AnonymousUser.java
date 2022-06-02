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
 * 匿名用户，临时用户
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_anonymous_user")
@ApiModel(value="AnonymousUser对象", description="匿名用户，临时用户")
public class AnonymousUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "密钥")
    private String secretKey;

    @ApiModelProperty(value = "短链接")
    private String shortUrl;

    @ApiModelProperty(value = "长链接，可能带有参数")
    private String longUrl;

    @ApiModelProperty(value = "匿名用户创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean deleted;


}
