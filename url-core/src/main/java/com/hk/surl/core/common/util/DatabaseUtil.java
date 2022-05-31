package com.hk.surl.core.common.util;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * @author : HK意境
 * @ClassName : DatabaseUtil
 * @date : 2022/5/30 9:46
 * @description : 数据库工具类
 * @Todo : 连接数据库，获取数据源
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class DatabaseUtil {

    // 数据库用户名: 默认 root
    private String username = "root";

    // 数据库密码: 默认 root
    private String password = "root";

    // 数据库 url
    private String url ;


    /**
     * @methodName : getDataSource
     * @author : HK意境
     * @date : 2022/5/30 9:56
     * @description : 获取 druid 数据源,获取数据源
     * @Todo : 通过外部参数获取数据源
     * @params :
         * @param : String username, String password, String url, Class<Driver> driverClass
     * @return : DataSource
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public static DataSource getDataSource(String username, String password, String url, Class<? extends Driver> driverClass){

       return getDatasource(username,password,url,driverClass.getName());
    }


    // 获取数据源
    public static DataSource getDatasource(String username, String password, String url,String driverClassName){

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        dataSource.setDriverClassName(driverClassName);

        return dataSource ;
    }



}
