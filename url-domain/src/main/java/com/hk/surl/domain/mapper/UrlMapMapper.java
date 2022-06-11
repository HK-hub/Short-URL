package com.hk.surl.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.UrlMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 长链接和短链接的映射关系 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface UrlMapMapper extends BaseMapper<UrlMap> {

    // 查询短链接id 对应的长链接 id 集合
    // select m.long_id from tb_url_map as m where m.short_id = #{shortId}
    List<String> selectLongIdByShortId(String shortId);


    // 通过 短链接id 值查询所有
    @Select("select * from tb_url_map where short_id = #{shortId} "+ "and visible = true")
    List<UrlMap> selectAllByShortId(String shortId);

    // 通过短链接字符串查询所有
    @Select("select * from tb_url_map where short_url = #{shortUrl} "+ "and visible = true")
    List<UrlMap> selectByShortUrl(String shortUrl);

    // 通过长链接id 查询所有，但是其实是长链接对短链接是多对一的，所有查询出来也只有一个
    @Select("select * from tb_url_map where long_id = #{longId} "+ "and visible = true")
    List<UrlMap> selectAllByLongId(String longId);

    // 通过长链接字符串查询所有, 但是这里其实一个长链接只对应一个短链接，所有也就相当于查询一个
    @Select("select * from tb_url_map where long_url = #{longUrl} "+ "and visible = true")
    List<UrlMap> selectAllByLongUrl(String longUrl);

    // 通过长链接id, 短链接id 唯一查询一个映射对象
    @Select("select * from tb_url_map where short_id = #{shortId} and long_id = #{longId} " + "and visible = true")
    UrlMap selectByShortIdAndLongId(String shortId, String longId);

    // 通过长链接字符串，短链接字符串查询唯一一个映射对象
    @Select("select * from tb_url_mao where short_url = #{shortUrl} and long_url = #{longUrl} "+ "and visible = true" )
    UrlMap selectByShortUrlAndShortUrl(String shortUrl, String longUrl);

    // 通过长链接MD5值 查询映射对象
    @Select("select * from tb_url_map where long_md = #{md} " + "and visible = true")
    UrlMap selectByLongMd(String md);

    // 查询所有，包括不可见和逻辑删除的
    @Select("select * from tb_url_map where deleted=true or deleted = false")
    List<UrlMap> selectAllIgnoreVisibleAndDeleted();

    // 查询所有，包括不可见的
    @Select("select * from tb_url_map where deleted = false")
    List<UrlMap> selectAllIgnoreVisible();


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/11 21:17
     * @description : 查询指定时间内的新增映射对象，也就为指定时间内新增的短链接
     * @Todo :
     * @apiNote :
     * @params :
         * @param startTime 开始时间点
         * @param endTime 结束时间点
     * @return List<UrlMap>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Select("select * from tb_url_map where create_time between(#{startTime}, #{endTime}) " + "and visible = true")
    List<UrlMap> selectAllByCreateTime(LocalDateTime startTime, LocalDateTime endTime);



}
