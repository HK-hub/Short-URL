package com.hk.surl.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.entity.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 匿名用户，临时用户 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface AnonymousUserMapper extends BaseMapper<AnonymousUser> {


    // 匿名用户登录
    AnonymousUser anonymousUserLogin(@Param("shortUrl") String shortUrl, @Param("secretKey") String secretKey);


    // 根据短链接查询
    @Select("select * from tb_anonymous_user where short_url = #{shortUrl} and deleted = false")
    AnonymousUser selectOneByShortUrl(ShortUrl shortUrl);
}
