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
 * 用户User，Sass对外提供接口的使用用户
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_app_user")
@ApiModel(value="AppUser对象", description="用户User，Sass对外提供接口的使用用户")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "注册邮箱：日后进行数据报表发送")
    private String email;

    @ApiModelProperty(value = "是否为会员")
    private Boolean vip;

    @ApiModelProperty(value = "密钥，用户哪来获取报表数据信息的验证")
    private String secretKey;

    @ApiModelProperty(value = "可见性")
    private Boolean visible;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;


}
