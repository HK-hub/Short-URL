package com.hk.surl.service.core;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.mapper.AnonymousUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName : AnonymousUserServiceImpl
 * @author : HK意境
 * @date : 2022/5/26
 * @description :匿名用户，临时用户 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class AnonymousUserServiceImpl extends ServiceImpl<AnonymousUserMapper, AnonymousUser> implements IAnonymousUserService {

    @Resource
    private AnonymousUserMapper anonymousUserMapper;


    /**
     * @methodName : anonymousLogin
     * @author : HK意境
     * @date : 2022/6/21 16:29
     * @description : 匿名用户登录
     * @Todo :
     * @apiNote 根据短链接，secretKey 进行匿名用户登录
     * @params :
         * @param shortUrl 需要获取数据的短链接
         * @param secretKey 短链接颁发的secretKey
     * @return AnonymousUser
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public AnonymousUser anonymousLogin(String shortUrl, String secretKey) {

        // 构造查询构造器
        LambdaQueryChainWrapper<AnonymousUser> wrapper = new LambdaQueryChainWrapper<>(anonymousUserMapper);
        wrapper.eq(AnonymousUser::getShortUrl, shortUrl).eq(AnonymousUser::getSecretKey, secretKey);
        // 查询获取数据
        AnonymousUser user = anonymousUserMapper.selectOne(wrapper);

        // 判断是否存在，如果存在异步进行数据生成
        if (user != null) {

        }

        return null;
    }
}
