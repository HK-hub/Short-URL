package com.hk.surl.common.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * @ClassName : ParseURLPairUtil
 * @author : HK意境
 * @date : 2022/5/25
 * @description : 解析 URL 路径值的工具类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ParseURLPairUtil {

    public static String parseURLPair(Object o) throws Exception{
        Class<? extends Object> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, Object> map = new TreeMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(o);
            if(value != null) {
                map.put(name, value);
            }
        }
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> it = set.iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, Object> e = it.next();
            sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }
}
