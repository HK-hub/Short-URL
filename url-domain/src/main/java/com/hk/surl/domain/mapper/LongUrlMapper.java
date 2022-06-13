package com.hk.surl.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.LongUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 长链接 Mapper 接口
 * </p>
 *
 * @author HK意境
 * @since 2022-05-26
 */
@Mapper
public interface LongUrlMapper extends BaseMapper<LongUrl> {

    /**
     * @methodName : selectListByIds
     * @author : HK意境
     * @date : 2022/6/10 21:26
     * @description : 使用 xml 方式 in 集合查询，速度由于 mybatis plus 的批量查询
     * @Todo :
     * @apiNote : 根据 id 集合查询长链接对象集合
     * @params :
         * @param ids 长链接对象 id 集合列表
     * @return List<LongUrl>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    List<LongUrl> selectLongUrlListByIds(List<String> ids);

    // 通过 长链接字符串获取长链接字符串
    @Select("select * from tb_long_url where url = #{longUrl} limit 1")
    LongUrl selectOneByLongUrl(String longUrl);


}
