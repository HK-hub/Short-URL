package com.hk.surl.core.common.util;

import java.security.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
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


    public static void main(String[] args) {

        LocalDateTime localDateTime = LocalDateTimePlusTimeUnit(LocalDateTime.now(), 10, TimeUnit.HOURS);
        System.out.println(localDateTime);

    }


}
