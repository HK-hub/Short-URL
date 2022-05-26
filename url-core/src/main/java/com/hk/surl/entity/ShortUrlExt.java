package com.hk.surl.entity;

import com.hk.surl.core.enums.ExpirationStrategy;
import com.hk.surl.domain.entity.ShortUrl;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : ShortUrlExt
 * @date : 2022/4/14 9:53
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@NoArgsConstructor
public class ShortUrlExt extends ShortUrl {


    @ApiModelProperty(value = "原始的长链接")
    private String longUrl ;

    @ApiModelProperty(value = "生成后的短链接")
    private String shortUrl;

    @ApiModelProperty(value = "短链接类型: http 请求链接，二维码，base64")
    private Integer type;

    @ApiModelProperty(value = "编号:目前作用未知")
    private String no;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "过期时间单位,时间度量")
    private ExpirationStrategy expiration ;

    @ApiModelProperty(value = "过期时间：表示短链接从创建经过到使用到消亡的时间，是指失效的时间： expiration_time=create_time+有效时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "跟新时间 ")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "可见性:1可见，0不可见")
    private Boolean visible;


    public ShortUrlExt(String longUrl) {
        this.longUrl = longUrl;
    }


    public ShortUrlExt(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }
}
