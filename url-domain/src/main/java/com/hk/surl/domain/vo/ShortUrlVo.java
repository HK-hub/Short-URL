package com.hk.surl.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.entity.ShortUrl;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : ShortUrlVo
 * @date : 2022/6/11 21:43
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class ShortUrlVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "生成后的短链接")
    private String shortUrl;

    @ApiModelProperty(value = "原始长链接")
    private String longUrl ;

    @ApiModelProperty(value = "secretKey 安全key")
    private String secretKey ;

    @ApiModelProperty(value = "短链接类型: http 请求链接，二维码，base64")
    private Integer type ;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();

    @ApiModelProperty(value = "过期时间：表示短链接从创建经过到使用到消亡的时间，是指失效的时间： expiration_time=create_time+有效时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "跟新时间 ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime = this.createTime;

    public ShortUrlVo() {
    }

    public ShortUrlVo(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public ShortUrlVo(String shortUrl, String longUrl, String secretKey) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.secretKey = secretKey ;
    }


    public ShortUrlVo(ShortUrl shortUrl , AnonymousUser anonymousUser){
        this(shortUrl.getShortUrl(), anonymousUser.getLongUrl(),anonymousUser.getSecretKey());
        this.expirationTime = shortUrl.getExpirationTime();
        this.type = shortUrl.getType();
    }





}
