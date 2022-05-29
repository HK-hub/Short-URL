package com.hk.surl;

import com.hk.surl.core.provider.distributed.SnowFlakeProvider;
import com.hk.surl.core.provider.random.RandomStringProvider;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : RandomTest
 * @date : 2022/5/29 15:28
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class RandomTest {


    // 测试随机算法稳定性
    @Test
    public void testRandomProvider(){

        // 测试长度
        int lenght = 6;
        // 测试次数
        int sum = 100000 ;
        RandomStringProvider provider = new RandomStringProvider();

        for (int i = 0; i < sum; i++) {
            String shotUrl = provider.createWithRandom(6);
            System.out.println(shotUrl);
        }

    }



    // 观察短链接冲突率
    @Test
    public void conflictRate() throws InterruptedException {

        // 记录
        Set<String> set = new HashSet<>();

        // 冲突率计算公式：
        //      conflictRate=冲突个数/元素总数：
        //          对于 set 集合，不会存储 重复元数-> cr = (总数-set集合个数)/总数

        // 生产 1000000 百万数据
        int sum = 1000000;
        // 随机产生模拟
        RandomStringProvider provider = new RandomStringProvider();
        // 测试长度
        int length = 12;

        for (int i = 0; i < sum; i++) {
            String shotUrl = provider.createWithRandom(length);
            //System.out.println(withSnowFlake);
            set.add(shotUrl);
            //TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));

        }

        System.out.println("元素总数：1000000(一百万)");
        System.out.println("set 集合中个数: " +set.size());
        System.out.println("重复元素出现次数："+(sum-set.size()));
        // 计算冲突率
        double cr = (double)(sum-set.size())/sum;
        System.out.println("冲突率: " + cr);


    }




}
