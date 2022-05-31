package com.hk.surl.core.common.util;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.entity.TinyId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : LeafSegmentUtil
 * @date : 2022/5/30 10:59
 * @description : 号段发号器算法
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Slf4j
public class LeafSegmentUtil {

    // 数据源，用来操作数据库——> 通过 jdbcTemplate
    private DataSource dataSource ;


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/5/30 11:15
     * @description: 单个：获取业务标识下的 发号器 进行发号
     * @Todo :
     * @params :
         * @param : String bizType 业务标识
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public static Long nextId(String bizType){

        return nextId(bizType, 1).get(0);
    }

    /**
     * @methodName : nextId
     * @author : HK意境
     * @date : 2022/5/30 11:15
     * @description : 批量获取 id
     * @Todo :
     * @params :
         * @param : String bizType 业务标识
         * @param： int batchSize 批量个数
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public static List<Long> nextId(String bizType, int batchSize){

        return null;
    }

    // 获取下一个号段
    public static Long getNextSegment(String bizType, DataSource dataSource){

        // 获取jdbc
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // 获取下一个号段: nextSegment(step)=当前号段最大值+step
        //                          = (begin_id+step)+step
        // begin_id =

        return null ;
    }


    // 根据业务号获取 发号器配置，没有对应业务那就异步生产一个
    public static TinyId getTinyIdConfigByBizType(String bizType, DataSource dataSource){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // 获取 tinyId 信息
        String sql = "select * from tb_tiny_id where biz_type = ?";
        List<TinyId> tinyIdList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TinyId.class), bizType);

        TinyId tinyId = new TinyId();
        // 存在该业务配置
        if (tinyIdList.size() >= 1){
            tinyId = tinyIdList.get(0);

        }else {
            // 不存在对应业务的配置, 默认生产一个该业务配置
            // biz_type=bizType, begin_id = 10000 , max_id=10000, step = 10000,delta=1,remainder=10000
            tinyId.setBizType(bizType);
            tinyId.setBeginId(10000L);
            tinyId.setMaxId(tinyId.getBeginId());
            tinyId.setDelta(1);
            tinyId.setStep(10000);
            tinyId.setRemainder(tinyId.getStep());

        }

        return tinyId;
    }


}
