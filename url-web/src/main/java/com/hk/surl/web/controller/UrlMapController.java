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


    // 根据 短链接查询对应的所有长链接
    @PostMapping("/surl")
    public ResponseResult<List<LongUrl>> getLongUrsByShortUrl(String shortUrl){
        // 批量查询
        List<LongUrl> longUrls = urlMapService.getLongUrlListByShortUrl(shortUrl);

        return ResponseResult.SUCCESS().setData(longUrls);
    }






}
