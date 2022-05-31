package com.hk.surl;

import com.hk.surl.core.common.util.DatabaseUtil;
import com.hk.surl.core.provider.leaf.LeafSegmentProvider;
import com.hk.surl.entity.TinyId;
import com.mysql.cj.jdbc.Driver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : LeafSegmentTest
 * @date : 2022/5/30 13:18
 * @description : 发号器算法测试
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
public class LeafSegmentTest {

    // 数据库参数
    private static final String username = "root";
    private static final String password = "root" ;
    private static final String url = "jdbc:mysql://localhost:3306/db_short_url";
    private static final Class<Driver> driver = Driver.class ;


    // 测试 double 运算
    @Test
    public void testRemainderRate(){

        int remainder = 200 ;
        int step = 1000 ;

        double rate = (double) remainder/step;

        System.out.println(rate);
        double limit = (double) 20/100 ;
        System.out.println(limit);
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/5/31 17:19
     * @description : 测试获取指定业务标识符的发号器配置
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Test
    public void testTinyIdConfig(){

        // 获取数据源
        DataSource datasource = DatabaseUtil.getDatasource(LeafSegmentTest.username, password, url, driver.getName());

        // 获取 jdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);

        // 查询指定 config
        String bizType = "short_url" ;
        String sql = "select * from tb_tiny_id where biz_type = ?";

        // 查询业务配置
        List<TinyId> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TinyId.class), bizType);

        System.out.println(query);

    }


    /**
     * @methodName : getNextIdTest
     * @author : HK意境
     * @date : 2022/5/31 17:26
     * @description : 获取下一个id 号
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Test
    public void getNextIdTest(){

        // 环境准备
        String bizType = "short_url" ;
        DataSource datasource = DatabaseUtil.getDatasource(LeafSegmentTest.username, password, url, driver.getName());

        // 创建 provider 发号器
        LeafSegmentProvider provider = new LeafSegmentProvider(bizType, datasource);

        // 生产号段
        Long nextId = provider.getNextId(provider.tinyIdConfig().getBizType());

        System.out.println(nextId);

    }


    // 循环获取 id 号
    @Test
    public void getNextIdListTest(){

        // 环境准备
        String bizType = "short_url" ;
        DataSource datasource = DatabaseUtil.getDatasource(LeafSegmentTest.username, password, url, driver.getName());

        // 创建 provider 发号器
        LeafSegmentProvider provider = new LeafSegmentProvider(bizType, datasource);

        // 生产号段
        for (int i = 0; i < 20000; i++) {
            Long nextId = provider.getNextId(null);
            System.out.println(nextId);
        }


    }



    // 测试批量获取 id 号
    @Test
    public void getNextIdBatchTest(){

        // 环境准备
        String bizType = "short_url" ;
        DataSource datasource = DatabaseUtil.getDatasource(LeafSegmentTest.username, password, url, driver.getName());

        // 创建 provider 发号器
        LeafSegmentProvider provider = new LeafSegmentProvider(bizType, datasource);

        List<Long> ids = provider.getNextIdByBatch(32100);

        // 保存环境
        provider.saveContext();


        System.out.println(ids);

    }


    /**
     * @methodName :providerTest
     * @author : HK意境
     * @date : 2022/5/31 19:53
     * @description : 百万 id 号获取
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Test
    public void providerTest(){

        // 环境准备
        String bizType = "short_url" ;
        DataSource datasource = DatabaseUtil.getDatasource(LeafSegmentTest.username, password, url, driver.getName());

        // 创建 provider 发号器
        LeafSegmentProvider provider = new LeafSegmentProvider(bizType, datasource);

        ArrayList<String> list = new ArrayList<>();
        long s = System.currentTimeMillis();


        for (int i = 0; i < 1000; i++) {
            list.add(provider.provideShortUrl(null));
        }
        long e = System.currentTimeMillis();
        System.out.println(list);

        log.info("开始时间:{}",s);
        log.info("结束时间:{}",e );
        log.info("时长:{}",(double)(e-s)/1000);
    }

}
