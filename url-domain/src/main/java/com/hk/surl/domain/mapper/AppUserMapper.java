package com.hk.surl.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户User，Sass对外提供接口的使用用户 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface AppUserMapper extends BaseMapper<AppUser> {

}
