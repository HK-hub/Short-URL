package com.hk.surl.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.UrlMap;
import org.apache.ibatis.annotations.Mapper;

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

}
