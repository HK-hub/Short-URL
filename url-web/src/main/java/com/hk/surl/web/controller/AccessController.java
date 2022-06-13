package com.hk.surl.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hk.surl.api.core.IShortUrlService;
import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.core.AccessService;
import com.hk.surl.service.core.ShortUrlServiceImpl;
import com.hk.surl.service.core.UrlMapServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author : HK意境
 * @ClassName : AccessController
 * @date : 2022/6/12 13:30
 * @description : 短链接访问跳转控制器
 * @Todo : 302 重定向
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@RestController
//@RequestMapping("/access")
@RequestMapping("/")
public class AccessController {

    // 拿到域名地址
    @Value("${short.url.system.domain}")
    private String domain ;

    @Resource
    private IUrlMapService urlMapService ;
    @Resource
    private IShortUrlService shortUrlService;
    @Resource
    private AccessService accessService ;



    /**
     * @methodName : redirectUrl
     * @author : HK意境
     * @date : 2022/6/12 15:27
     * @description : 查询缓冲短链接对应的长链接，查询数据库对应的长链接
     * @Todo :
     * @apiNote : 根据短链接跳转到对应的长链接
     * @params :
         * @param surl 短链接字符串
     * @return ResponseResult
     * @throws:
     * @Bug : favor.ico 图标存在访问问题，浏览器首先会访问图标然后在访问短链接
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/{surl}")
    public ResponseResult<ShortUrlVo> redirectUrl(@PathVariable(name = "surl") String surl,
                                                  HttpServletRequest request ,
                                                  HttpServletResponse response) throws IOException {
        // 参数校验
        Assert.notEmpty(surl, "shortUrl must be not null but provide is a null string");

        // 获取参数: 完整的短链接地址
        String shortUrl = this.domain + "/" + surl ;
        //log.info("shortUrl: {}", shortUrl);

        // 获取到需要重定向的长链接对象: 根据选择策略
        LongUrl redirectLongUrl = this.accessService.getRedirectLongUrl(shortUrl);

        // 日志
        log.info("access shortUrl: {} -> {}",shortUrl, redirectLongUrl.getUrl());

        // 302 重定向
        response.sendRedirect(redirectLongUrl.getUrl());

        return ResponseResult.SUCCESS().setResultCode(ResultCode.REDIRECT_TEMPORARY)
                .setData(new ShortUrlVo(shortUrl,redirectLongUrl.getUrl()));
    }






}
