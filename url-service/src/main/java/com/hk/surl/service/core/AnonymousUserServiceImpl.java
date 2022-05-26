package com.hk.surl.service.core;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.mapper.AnonymousUserMapper;
import org.springframework.stereotype.Service;

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


}
