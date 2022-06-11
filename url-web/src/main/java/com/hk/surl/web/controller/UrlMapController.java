package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.UrlMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@RestController
@RequestMapping("/url-map")
public class UrlMapController {

    @Autowired
    private IUrlMapService urlMapService ;


    /**
     * @methodName : getLongUrsByShortUrl
     * @author : HK意境
     * @date : 2022/6/11 19:28
     * @description : 根据短链接字符串获取全部的长链接实体
     * @Todo :
     * @apiNote : 根据短链接字符串获取全部的长链接实体
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
     * @apiNote : 通过 短链接 id 获取对应的长链接对象
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






}
