package com.hk.surl.common.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * @author : HK意境
 * @ClassName : LocationUtil
 * @date : 2022/6/20 16:03
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LocationUtil {

    // 腾讯位置服务 key
    @Value("${short.url.system.location.key}")
    private String key;
    @Value("${short.url.system.location.url}")
    private String locationUrl ;

    //使用腾讯的接口通过ip拿到城市信息
    public String getCityInfo(String ip)  {
        // 请求结果
        String s = sendGet(ip, key);
        Map map = JSONObject.parseObject(s, Map.class);

        String message = (String) map.get("message");
        if("query ok".equals(message)){
            Map result = (Map) map.get("result");
            Map addressInfo = (Map) result.get("ad_info");
            // 获取国家，省份，城市信息
            String nation = (String) addressInfo.get("nation");
            String province = (String) addressInfo.get("province");
            //  String district = (String) addressInfo.get("district");
            String city = (String) addressInfo.get("city");
            String address = nation + "-" + province + "-" + city;
            return address;
        }else{
            System.out.println(message);
            return null;
        }
    }

    //根据在腾讯位置服务上申请的key进行请求操作
    private String sendGet(String ip, String key) {
        String result = "";
        BufferedReader in = null;
        try {
            // 位置服务请求地址
            //String urlNameString = this.locationUrl.replace("{IP}",ip);
            String urlNameString = "https://apis.map.qq.com/ws/location/v1/ip?ip="+ip+"&key="+key;

            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (Map.Entry entry : map.entrySet()) {
                System.out.println(key + "--->" + entry);
            }*/

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
