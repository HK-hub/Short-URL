package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.service.core.AnonymousUserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName : AnonymousUserController
 * @author : HK意境
 * @date : 2022/5/26 15:32
 * @description :匿名用户，临时用户 前端控制器
 * @Todo : 这里需要用来用户短链接数据的认证, 用户短链接数据的获取
 *
 * @Bug : 对于所有的短链接，都使用一个 secret_key 进行校验
 * @Modified :
 * @Version : 1.0
 */
@RestController
@RequestMapping("/anonymous")
public class AnonymousUserController {

    @Resource
    private IAnonymousUserService anonymousUserService ;


    // 匿名用户使用 短链接 和 secret_key 进行登录
    @PostMapping("/login")
    public ResponseResult<AnonymousUser> login(@RequestParam(name = "ShortUrl")String shortUrl,
                                               @RequestParam(name = "secretKey")String secretKey){

        // 如果存在 secret_key 则生成token
        AnonymousUser user = anonymousUserService.anonymousLogin(shortUrl, secretKey);


    }


}
