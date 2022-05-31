package com.hk.surl;

import com.hk.surl.core.common.util.DatabaseUtil;
import com.hk.surl.domain.entity.LongUrl;
import com.mysql.cj.jdbc.Driver;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : JDBCTest
 * @date : 2022/5/30 9:44
 * @description : 数据库操作测试
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class JDBCTest {

    private static final String username = "root";
    private static final String password = "root" ;
    private static final String url = "jdbc:mysql://localhost:3306/db_short_url";
    private static final Class<Driver> driver = Driver.class ;


    // 测试数据库连接,查询
    @Test
    public void jdbcConnectTest(){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(DatabaseUtil.getDataSource(username, password, url, driver));

        //String sql = "select count(*) from tb_long_url";
        String sql = "select * from tb_long_url";
        //Integer count = jdbcTemplate.queryFor
        // Object(sql, Integer.class);

        List<LongUrl> urlList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(LongUrl.class));


        System.out.println(urlList);
    }


}
