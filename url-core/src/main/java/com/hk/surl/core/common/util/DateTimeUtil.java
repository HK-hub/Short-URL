package com.hk.surl.core.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : DateTimeUtil
 * @date : 2022/5/31 23:41
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class DateTimeUtil {

    // 将LocalDateTime转为自定义的时间格式的字符串
    public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    // 将long类型的timestamp转为LocalDateTime
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    // 将某时间字符串转为自定义时间格式的LocalDateTime
    public static LocalDateTime parseStringToDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }


    // LocalDateTime 加上 TimeUnit 时间
    public static LocalDateTime LocalDateTimePlusTimeUnit(LocalDateTime dateTime ,long times, TimeUnit unit){

        Long timestamp = dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long mills = TimeUnit.MILLISECONDS.convert(times, unit);

        return Instant.ofEpochMilli(timestamp+mills).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }



    // 将 Integer time  和 String timeUnit 参数转换为一个 TimeUnit
    public static Map.Entry<Integer,TimeUnit> convertTimeConfigToTimeUnit(Integer time , String unit){

        // 设置时间单位，默认为 天
        TimeUnit timeUnit = TimeUnit.DAYS ;
        // 获取时间单位
        if ("month".equalsIgnoreCase(unit)){
            // 时间单位月
            time = 30 ;

        }else if("day".equalsIgnoreCase(unit)){
            // 时间单位为天
            timeUnit = TimeUnit.DAYS ;

        }else if("year".equalsIgnoreCase(unit) && time <= 99){
            // 时间单位为年, 要求 time < 99
            timeUnit = TimeUnit.DAYS ;
            // 一年 365 天
            time *= 365 ;

        }else if ("forever".equalsIgnoreCase(unit)){
            // 永不过期
            time = -1;

        }else{
            // 参数错误, 设置过期时间为 1 天，防止恶意攻击
            timeUnit = TimeUnit.DAYS ;
            time = 1;
        }

        // 返回时间 entry 对
        return Map.entry(time, timeUnit);

    }

    // 根据时间对，偏移现在的时间-> 现在时间加上时间对参数
    public static LocalDateTime nowPlusTimeUnit(Map.Entry<Integer, TimeUnit> timeEntry){

        LocalDateTime expirationTime = null ;

        // 判断是否为永久有效
        if (timeEntry.getKey() == -1){
            // 永久有效
            expirationTime = LocalDateTime.MAX ;

        }else{
            // 有过期时间
            expirationTime = DateTimeUtil.LocalDateTimePlusTimeUnit(LocalDateTime.now(),
                    timeEntry.getKey(),timeEntry.getValue());
        }

        return expirationTime ;
    }

    // 将 Integer time  和 String timeUnit 参数转换为 过期时间
    public static LocalDateTime getExpirationTimeByTimeEntry(Integer time , String unit){

        // 转换为 entry 单位
        Map.Entry<Integer, TimeUnit> entry = DateTimeUtil.convertTimeConfigToTimeUnit(time, unit);
        // 偏移时间->过期时间
        LocalDateTime expirationTime = DateTimeUtil.nowPlusTimeUnit(entry);

        return expirationTime ;
    }


    public static void main(String[] args) {

        LocalDateTime localDateTime = LocalDateTimePlusTimeUnit(LocalDateTime.now(), 10, TimeUnit.HOURS);
        System.out.println(localDateTime);

    }


}
