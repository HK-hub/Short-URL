package com.hk.surl.api.core;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hk.surl.domain.entity.AnonymousUser;

/**
 * @ClassName : IAnonymousUserService
 * @author : HK意境
 * @date : 2022/5/26
 * @description : 匿名用户，临时用户 服务类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public interface IAnonymousUserService extends IService<AnonymousUser> {

    // 匿名用户登录
    AnonymousUser anonymousLogin(String shortUrl, String secretKey);
}
