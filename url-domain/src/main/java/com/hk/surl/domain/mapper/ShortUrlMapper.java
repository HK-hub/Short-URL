package com.hk.surl.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.ShortUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 短链接表 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface ShortUrlMapper extends BaseMapper<ShortUrl> {

    // 因为这里，一个长链接只能生成对应的唯一的一个短链接，但是我们的一个短链接对象是可以对应多个长链接对象的
    // 根据 根据长链接对象 id 关联查询 url_map 表，获取对应 短链接 id
    // select su.*  from tb_short_url as su where su.id in (
    //      select m.short_url_id from tn_url_map as m where m.long_url = #{longUrl} and m.visible = true ;
    // )
    ShortUrl selectByLongUrl(LongUrl longUrl);

    // 查询指定时间内新增的短链接
    /**
     * @methodName : selectAllByCreateTime
     * @author : HK意境
     * @date : 2022/6/11 21:35
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param startTime 开始时间
         * @param endTime 结束时间
     * @return List<ShortUrl>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Select("select * from tb_short_url where create_time between(#{startTime,endTime})")
    List<ShortUrl> selectAllByCreateTime(LocalDateTime startTime , LocalDateTime endTime);

    // 查询指定有效期内的短链接对象
    /**
     * @methodName : selectAllByExpirationTime
     * @author : HK意境
     * @date : 2022/6/11 21:37
     * @description : 查询过期时间在指定范围的短链接数量
     * @Todo :
     * @apiNote :
     * @params :
         * @param startTime 开始时间
         * @param expirationTime 结束时间，过期时间
     * @return List<ShortUrl>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Select("select * from tb_short_url where expiration_time between(#{startTime}, #{expirationTime}")
    List<ShortUrl> selectAllByExpirationTime(LocalDateTime startTime ,LocalDateTime expirationTime);




}
