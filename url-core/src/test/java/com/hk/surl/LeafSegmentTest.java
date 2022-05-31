package com.hk.surl;

import org.junit.Test;

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
public class LeafSegmentTest {

    @Test
    public void testRemainderRate(){

        int remainder = 200 ;
        int step = 1000 ;

        double rate = (double) remainder/step;

        System.out.println(rate);
        double limit = (double) 20/100 ;
        System.out.println(limit);
    }



}
