package com.hk.surl.web.controller;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.core.AccessService;
import com.hk.surl.web.aop.SysLog;
import com.hk.surl.web.aop.processor.AccessProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @SysLog(businessType = "访问短链接重定向到长链接",operate = "访问重定向", processor = AccessProcessor.class)
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

        // 构造响应对象
        ResponseResult<ShortUrlVo> result = new ResponseResult<>(ResultCode.REDIRECT_TEMPORARY, new ShortUrlVo(shortUrl, redirectLongUrl.getUrl()));
        return result;
    }






}
