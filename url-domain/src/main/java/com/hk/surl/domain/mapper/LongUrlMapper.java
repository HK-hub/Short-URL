package com.hk.surl.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hk.surl.domain.entity.LongUrl;
import org.apache.ibatis.annotations.Mapper;


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

}
