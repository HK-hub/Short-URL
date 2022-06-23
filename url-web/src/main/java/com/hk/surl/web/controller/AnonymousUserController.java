package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.service.core.AnonymousUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/anonymous")
public class AnonymousUserController {

    @Resource
    private IAnonymousUserService anonymousUserService ;

    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/22 0:44
     * @description : 匿名用户使用 短链接 和 secret_key 进行登录
     * @Todo : 后续查看所有数据都需要在请求头中带上 secret_key
     * @apiNote 匿名用户使用 短链接 和 secret_key 进行登录
     * @params :
         * @param shortUrl 短链接
         * @param secretKey 安全密钥
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping(value = "/login")
    public ResponseResult<AnonymousUser> login( String shortUrl, String secretKey){

        // 参数校验
        //log.info("{},{}",shortUrl,secretKey);

        // 如果存在 secret_key 则生成token
        AnonymousUser user = anonymousUserService.anonymousLogin(shortUrl, secretKey);

        // 没有此短链接，或者安全密钥错误
        ResponseResult<AnonymousUser> result = new ResponseResult<>(ResultCode.USER_LOGIN_ERROR);
        result.setData(user);
        if (user != null) {
            result.setResultCode(ResultCode.SUCCESS);
        }

        return result;
    }







}
