package com.hk.surl;

import com.hk.surl.core.provider.distributed.SnowFlakeProvider;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : SnowFlakeTest
 * @date : 2022/5/29 14:40
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class SnowFlakeTest {


    // 生成后的 id 是一个 64 位的 long 值，数值长度为 19 为，但是前面高位很长部分都是一样的，可以去除前面的高位
    @Test
    public void idWorkerTest(){

        for (int i = 0; i < 100; i++) {
            String shotUrl = String.valueOf(SnowFlakeProvider.idWorker.nextId());
            System.out.println(shotUrl);
        }

    }



    // 观察规律
    // 结论：雪花算法，生成的是 64 位的 long 数字，前面1-42 区间内为 41 位的时间戳，所以高位基本上不会变，低位可能变化过快出现重复值
    @Test
    public void snowflakeUrlTestV1(){

        // 生产 1000000 百万数据
        for (int i = 0; i < 1000000; i++) {
            String withSnowFlake = SnowFlakeProvider.createWithSnowFlake(12);
            System.out.println(withSnowFlake);
        }

    }


    @Test
    public void snowflakeUrlTestV2(){

        // 生产 1000000 百万数据
        for (int i = 0; i < 1000000; i++) {
            String withSnowFlake = SnowFlakeProvider.createWithSnowFlake(9);
            System.out.println(withSnowFlake);
        }

    }

    // 观察短链接冲突率
    @Test
    public void conflictRateV1() throws InterruptedException {
        System.out.println("---------------------V1-----------------------");

        // 记录
        Set<String> set = new HashSet<>();

        // 冲突率计算公式：
        //      conflictRate=冲突个数/元素总数：
        //          对于 set 集合，不会存储 重复元数-> cr = (总数-set集合个数)/总数

        // 生产 1000000 百万数据
        int sum = 1000000;
        // 随机产生模拟
        Random random = new Random();
        for (int i = 0; i < sum; i++) {
            String shotUrl = String.valueOf(SnowFlakeProvider.createWithSnowFlake(6));
            //System.out.println(withSnowFlake);
            set.add(shotUrl);


        }

        System.out.println("元素总数：1000000(一百万)");
        System.out.println("set 集合中个数: " +set.size());
        System.out.println("重复元素出现次数："+(sum-set.size()));
        // 计算冲突率
        double cr = (double)(sum-set.size())/sum;
        System.out.println("冲突率: " + cr);

    }



    // 观察短链接冲突率V2
    @Test
    public void conflictRateV2() throws InterruptedException {

        // 记录
        Set<String> set = new HashSet<>();

        // 冲突率计算公式：
        //      conflictRate=冲突个数/元素总数：
        //          对于 set 集合，不会存储 重复元数-> cr = (总数-set集合个数)/总数

        // 生产 1000000 百万数据
        int sum = 1000;
        // 随机产生模拟
        Random random = new Random();
        for (int i = 0; i < sum; i++) {
            String shotUrl = SnowFlakeProvider.createWithSnowFlake(6);
            //System.out.println(withSnowFlake);
            set.add(shotUrl);
        }

        System.out.println("元素总数：1000000(一百万)");
        System.out.println("set 集合中个数: " +set.size());
        System.out.println("重复元素出现次数："+(sum-set.size()));
        // 计算冲突率
        double cr = (double)(sum-set.size())/sum;
        System.out.println("冲突率: " + cr);


    }



}

