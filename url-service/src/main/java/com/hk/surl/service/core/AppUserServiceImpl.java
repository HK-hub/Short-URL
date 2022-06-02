package com.hk.surl.service.core;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IAppUserService;
import com.hk.surl.domain.entity.AppUser;
import com.hk.surl.domain.mapper.AppUserMapper;
import org.springframework.stereotype.Service;

/**
 * @ClassName : AppUserServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:17
 * @description :用户User，Sass对外提供接口的使用用户 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {



}
