package com.hk.surl.common.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.hk.surl.common.entity.Location;
import com.hk.surl.common.entity.VisitorAgent;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : IPUtil
 * @author : HK意境
 * @date : 2022/5/25
 * @description : 获取用户 IP 信息工具类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class IPUtil {



    // 创建 GeoLite2 数据库
    private static InputStream database;
    // 数据库读取对象
    private static DatabaseReader reader ;

    // ip2
    private static DbSearcher searcher ;
    private static Method method;

    static {

        // geoLite2
        try {
            database = new FileInputStream("url-common/src/main/resources/GeoLite2-City.mmdb");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {

        }

        File file =  new File("url-common/src/main/resources/ip2region.db");
        if (!file.exists()) {
            System.out.println("Error: Invalid ip2region.db file, filePath：" + file.getPath());
        }

        //查询算法
        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
        //DbSearcher.BINARY_ALGORITHM //Binary
        //DbSearcher.MEMORY_ALGORITYM //Memory
        try{
            DbConfig config = new DbConfig();
            searcher = new DbSearcher(config, file.getPath());
            switch ( algorithm ){
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    // 获取 ip 地址
    public static String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.contains(",")){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")  || ip.endsWith("0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }


    // 获取请求ip 者的国家，省份，城市，
    public static Location getIpAddressLocation(String ipAddress)  {

        Location location = new Location();

        InetAddress address = null;
        try {
            address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // 获取查询结果
        CityResponse response = null;
        try {
            response = reader.city(address);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        // 获取国家
        location.setCountry(response.getCountry().getNames().get("zh-CN"));
        // 获取身份
        location.setProvince(response.getLeastSpecificSubdivision().getNames().get("zh-CN"));
        // 获取城市
        location.setCity(response.getCity().getNames().get("zh-CN"));

        return location;
    }

    /**
     * 根据IP地址获取城市
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) throws IOException, InvocationTargetException, IllegalAccessException {
        DataBlock dataBlock;
        if (!Util.isIpAddress(ip)) {
            System.out.println("Error: Invalid ip address");
            return null;
        }
        dataBlock  = (DataBlock) method.invoke(searcher, ip);
        return dataBlock.getRegion();
    }


    public static VisitorAgent getUserAgent(String userAgent) {

        if(userAgent==""||userAgent==null){

            userAgent="";
        }
        if (userAgent.contains("Windows")) {

            if (userAgent.contains("Windows NT 10.0")) {

                return judgeBrowser(userAgent, "Windows 10");

            } else if (userAgent.contains("Windows NT 6.2")) {

                return judgeBrowser(userAgent, "Windows 8");

            } else if (userAgent.contains("Windows NT 6.1")) {

                return judgeBrowser(userAgent, "Windows 7" );

            } else if (userAgent.contains("Windows NT 6.0")) {

                return judgeBrowser(userAgent, "Windows Vista");

            } else if (userAgent.contains("Windows NT 5.2")) {

                return judgeBrowser(userAgent, "Windows XP");

            } else if (userAgent.contains("Windows NT 5.1")) {

                return judgeBrowser(userAgent, "Windows XP");

            } else if (userAgent.contains("Windows NT 5.01")) {

                return judgeBrowser(userAgent, "Windows 2000");

            } else if (userAgent.contains("Windows NT 5.0")) {

                return judgeBrowser(userAgent, "Windows 2000");

            } else if (userAgent.contains("Windows NT 4.0")) {

                return judgeBrowser(userAgent, "Windows NT 4.0");

            } else if (userAgent.contains("Windows 98; Win 9x 4.90")) {

                return judgeBrowser(userAgent, "Windows ME");

            } else if (userAgent.contains("Windows 98")) {

                return judgeBrowser(userAgent, "Windows 98");

            } else if (userAgent.contains("Windows 95")) {

                return judgeBrowser(userAgent, "Windows 95");

            } else if (userAgent.contains("Windows CE")) {

                return judgeBrowser(userAgent, "Windows CE");

            }

        } else if (userAgent.contains("Mac OS X")) {

            if(userAgent.contains("iPhone")){

                return judgeBrowser(userAgent, "iPhone");

            }

            else if (userAgent.contains("iPad")) {

                return judgeBrowser(userAgent, "iPad");//判断系统

            }else{

                return judgeBrowser(userAgent, "Mac");//判断系统

            }

        }else if(userAgent.contains("Android")){

            return judgeBrowser(userAgent, "Android");//判断系统

        }else if(userAgent.contains("Linux")){

            return judgeBrowser(userAgent, "Linux");//判断系统

        }else if(userAgent.contains("FreeBSD")){

            return judgeBrowser(userAgent, "FreeBSD");//判断系统

        }else if(userAgent.contains("Solaris")){

            return judgeBrowser(userAgent, "Solaris");//判断系统

        }

        return judgeBrowser(userAgent, "其他");

    }


    private static VisitorAgent judgeBrowser(String userAgent, String platformType) {

        if (userAgent.contains("Edge")) {

            return new VisitorAgent("Microsoft Edge", platformType);

        }else if(userAgent.contains("QQBrowser")){

            return new VisitorAgent("QQ浏览器", platformType);

        }else if (userAgent.contains("Chrome")&&userAgent.contains("Safari")) {

            return new VisitorAgent("Chrome", platformType);

        } else if (userAgent.contains("Firefox")) {

            return new VisitorAgent("Firefox",platformType);

        }else if (userAgent.contains("360")) {//Internet Explorer 6

            return new VisitorAgent("360浏览器", platformType);

        }else if (userAgent.contains("Opera")) {//Internet Explorer 6

            return new VisitorAgent("Opera", platformType);

        }else if (userAgent.contains("Safari")&&!userAgent.contains("Chrome")) {

            return new VisitorAgent("Safari", platformType);

        }else if (userAgent.contains("MetaSr1.0")) {//Internet Explorer 6

            return new VisitorAgent("搜狗浏览器", platformType);

        }else if (userAgent.contains("TencentTraveler")) {//Internet Explorer 6

            return new VisitorAgent("腾讯浏览器", platformType);

        }else if (userAgent.contains("UC")) {//Internet Explorer 6

            return new VisitorAgent("UC浏览器", platformType);

        }else if (userAgent.contains("MSIE")) {

            if (userAgent.contains("MSIE 10.0")) {//Internet Explorer 10

                return new VisitorAgent("IE 10", platformType);

            } else if (userAgent.contains("MSIE 9.0")) {//Internet Explorer 9

                return new VisitorAgent("IE 9", platformType);

            } else if (userAgent.contains("MSIE 8.0")) {//Internet Explorer 8

                return new VisitorAgent("IE 8", platformType);

            } else if (userAgent.contains("MSIE 7.0")) {//Internet Explorer 7

                return new VisitorAgent("IE 7", platformType);

            } else if (userAgent.contains("MSIE 6.0")) {//Internet Explorer 6

                return new VisitorAgent("IE 6", platformType);

            }
        } else {//暂时支持以上三个主流.其它浏览器,待续...

            return new VisitorAgent("其他", platformType);
        }
        return new VisitorAgent("其他", platformType);
    }

}

