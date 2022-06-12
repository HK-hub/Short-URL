package com.hk.surl.web.controller;

import cn.hutool.core.lang.Assert;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
         * @param shortUrl 短链接字符串
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/{shortUrl}")
    public ResponseResult<ShortUrlVo> redirectUrl(@PathVariable(name = "shortUrl", required = true)String shortUrl,
                                                  HttpServletRequest request ,
                                                  HttpServletResponse response) throws IOException {
        // 参数校验
        Assertions.assertThat(shortUrl).isNotNull();

        // 获取参数: 完整的短链接地址
        shortUrl = this.domain + "/" + shortUrl ;

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
