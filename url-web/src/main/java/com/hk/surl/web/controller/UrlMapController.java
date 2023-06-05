package com.hk.surl.web.controller;

import com.hk.surl.api.common.FileService;
import com.hk.surl.api.core.IShortUrlService;
import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.core.common.util.DateTimeUtil;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.domain.entity.UrlMap;
import com.hk.surl.service.util.ExcelResolveService;
import com.hk.surl.web.aop.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName : UrlMapController
 * @author : HK意境
 * @date : 2022/5/26 15:36
 * @description :长链接和短链接的映射关系 前端控制器
 * @Todo : 完成的是对接长链接，短链接数据的相互关联转换获取
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@RestController
@RequestMapping("/url-map")
public class UrlMapController {

    @Value("${short.url.system.longUrl.template}")
    private String longUrlTemplate ;
    @Resource
    private IUrlMapService urlMapService ;
    @Resource
    private FileService fileService ;
    @Resource
    private ExcelResolveService excelResolveService;
    @Resource
    private IShortUrlService shortUrlService ;

    /**
     * @methodName : getLongUrsByShortUrl
     * @author : HK意境
     * @date : 2022/6/11 19:28
     * @description : 根据短链接字符串获取全部的长链接实体
     * @Todo :
     * @apiNote 根据短链接字符串获取全部的长链接实体
     * @params :
         * @param shortUrl 短链接字符串
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/get/surl")
    public ResponseResult<List<LongUrl>> getLongUrsByShortUrl(String shortUrl){
        // 批量查询
        List<LongUrl> longUrls = urlMapService.getLongUrlListByShortUrl(shortUrl);

        return ResponseResult.SUCCESS().setData(longUrls);
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/11 19:28
     * @description :
     * @Todo :
     * @apiNote 通过 短链接 id 获取对应的长链接对象
     * @params :
         * @param sid 短链接id值
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/get/sid")
    public ResponseResult<List<LongUrl>> getLongUrlsByShortUrlId(String sid){
        // 查询集合
        List<LongUrl> longUrls = urlMapService.getLongUrlListBySId(sid);

        return ResponseResult.SUCCESS().setData(longUrls);
    }


    // 批量生成短链接
    /**
     * @methodName : createBatchUrlMap
     * @author : HK意境
     * @date : 2022/6/13 20:26
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param file 长链接excel 文件,建议数据量不要太大
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/new/batch")
    public ResponseResult<List<ShortUrl>> createBatchUrlMap(MultipartFile file,
                                            @RequestParam(name = "time" ,defaultValue = "1",required = false) Integer time ,
                                            @RequestParam(name = "timeUnit" ,defaultValue ="day", required = false)String timeUnit) throws IOException, ExecutionException, InterruptedException {

        // 获取输入流
        List<String> longUrls = excelResolveService.resolveImportExcel(file.getInputStream());

        // 构造过期时间
        LocalDateTime expirationTime = DateTimeUtil.getExpirationTimeByTimeEntry(time, timeUnit);

        // 批量生产
        List<ShortUrl> shortUrlList = this.shortUrlService.batchNewShortUrl(longUrls, expirationTime);

        return new ResponseResult<>(ResultCode.SUCCESS, shortUrlList) ;
    }


    /**
     * @methodName : createBatchUrlMapSimple
     * @author : HK意境
     * @date : 2022/6/30 10:15
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param file 短链接批量文件
         * @param expirationTime 过期时间
     * @return ResponseResult<List<ShortUrl>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/simple/new/batch")
    public ResponseResult<List<ShortUrl>> createBatchUrlMapSimple(MultipartFile file,@RequestParam(name = "expirationTime")String expirationTime) throws IOException, ExecutionException, InterruptedException {

        // 获取输入流
        List<String> longUrls = excelResolveService.resolveImportExcel(file.getInputStream());

        // 构造过期时间
        LocalDateTime expirationDate = LocalDateTime.parse(expirationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 批量生产
        List<ShortUrl> shortUrlList = this.shortUrlService.batchNewShortUrl(longUrls, expirationDate);

        return new ResponseResult<>(ResultCode.SUCCESS, shortUrlList) ;
    }



    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/13 20:38
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "下载长链接模板",operate = "下载")
    @GetMapping("/download/template")
    public void downloadLongUrlTemplate(HttpServletResponse response) throws IOException {

        // 默认版本：最新版本
        String path = this.longUrlTemplate;

        //log.info("template name : " + path);

        // 下载文件
        Boolean res = fileService.downloadFile(path, response);
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/15 14:36
     * @description :
     * @Todo :
     * @apiNote 通过长链接字符串查询UrlMap映射对象
     * @params :
         * @param longUrl 长链接字符串
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/get/lurl")
    public ResponseResult<UrlMap> getByLongUrl(String longUrl){

        UrlMap urlMap = this.urlMapService.getUrlMapByLongUrl(longUrl);

        return new ResponseResult<UrlMap>(ResultCode.SUCCESS, urlMap);
    }


    /**
     * @methodName : getByLongId
     * @author : HK意境
     * @date : 2022/6/15 15:37
     * @description :
     * @Todo :
     * @apiNote 通过长链接id字符串查询对应的映射对象
     * @params :
         * @param longId 长链接字符串id
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/get/lid")
    public ResponseResult<List<UrlMap>> getByLongId(String longId){

        // 查询
        List<UrlMap> urlMap = this.urlMapService.getUrlMapByLongId(longId);

        // 返回
        return new ResponseResult<List<UrlMap>>(ResultCode.SUCCESS, urlMap);
    }


    /**
     * @methodName : getAll
     * @author : HK意境
     * @date : 2022/6/15 14:32
     * @description :
     * @Todo :
     * @apiNote 获取全部urlMap 映射
     * @params :
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "获取全部UrlMap 映射对象", operate = "查询")
    @GetMapping("/get/all")
    public ResponseResult<List<UrlMap>> getAll(){

        List<UrlMap> urlMaps = this.urlMapService.list();

        return new ResponseResult<>(ResultCode.SUCCESS,urlMaps);
    }



}
