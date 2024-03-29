package com.hk.surl.web.controller;

import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.IVisitLogService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.core.AccessService;
import com.hk.surl.web.aop.SysLog;
import com.hk.surl.web.aop.processor.AccessProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
    @Resource
    private IVisitLogService visitLogService;


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
                                                  HttpServletResponse response, Device device) throws IOException {
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


    /**
     * @methodName : getAll
     * @author : HK意境
     * @date : 2022/6/15 20:51
     * @description :
     * @Todo :
     * @apiNote 查询全部短链接访问数据
     * @params :
     * @return nuResponseResultll
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "获取全部短链接访问日志",operate = "查询")
    @GetMapping("/access/get/all")
    public ResponseResult<List<VisitLog>> getAll(){

        List<VisitLog> list = this.visitLogService.list();

        return new ResponseResult<List<VisitLog>>(ResultCode.SUCCESS, list);
    }



    /**
     * @methodName : getListByShortUrl
     * @author : HK意境
     * @date : 2022/6/18 14:18
     * @description :
     * @Todo :
     * @apiNote 获取指定短链接的访问记录列表
     * @params :
         * @param shortUrl 短链接
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/access/get/surl")
    public ResponseResult<List<VisitLog>> getListByShortUrl(@RequestParam(name = "shortUrl")String shortUrl){

        // 构造查询条件,查询
        List<VisitLog> visitLogList = this.visitLogService.getListByShortUrl(shortUrl);

        // 响应结果
        return new ResponseResult<>(ResultCode.SUCCESS, visitLogList);
    }


    /**
     * @methodName : getAccessDataByShortUrl
     * @author : HK意境
     * @date : 2022/6/16 10:55
     * @description :
     * @Todo :
     * @apiNote 获取短链接的访问数据, 导出为 excel 文件
     * @params :
         * @param shortUrl 需要获取数据的短链接字符串
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/access/data/surl")
    public void getAccessDataByShortUrl(@RequestParam(name = "shortUrl")String shortUrl,
                                                                  HttpServletResponse response) throws IOException {

        // 查询数据，构造writer
        ExcelWriter excelWriter = this.visitLogService.exportAccessDataToExcel(shortUrl);

        // 响应到浏览器
        //设置content—type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //设置标题
        String fileName= URLEncoder.encode(shortUrl+"短链接访问数据", StandardCharsets.UTF_8);
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");
        ServletOutputStream outputStream= response.getOutputStream();

        //将Writer刷新到OutPut
        excelWriter.flush(outputStream,true);
        outputStream.close();
        excelWriter.close();

        // 响应结果
        return ;
    }

    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/18 14:36
     * @description :
     * @Todo :
     * @apiNote 获取指定日期内的访问数据
     * @params :
         * @param shorUrl 指定短链接
         * @param startTime 指定开始时间
         * @param endTime 指定结束时间
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/access/get/datetime")
    public ResponseResult<List<VisitLog>> getListByDateTime(@RequestParam(name = "shorUrl", required = false)String shorUrl,
                                                            @RequestParam(name = "startTime", required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
                                                            @RequestParam(name = "endTime",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime){
        // 参数校验
        //log.info("{},{},{}",shorUrl,startTime,endTime);

        // 查询数据
        List<VisitLog> visitLogList = this.visitLogService.getListByDateTime(shorUrl, startTime, endTime);

        // 响应数据
        return new ResponseResult<>(ResultCode.SUCCESS, visitLogList);
    }


}
