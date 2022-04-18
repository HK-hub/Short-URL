package com.hk.surl.core.strategy.generate;

import com.hk.surl.core.strategy.GenerateStrategy;
import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.IShortUrlGenerator;
import com.hk.surl.core.strategy.ShortUrlGeneratorBuilder;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * @author : HK意境
 * @ClassName : RandomStringStrategy
 * @date : 2022/4/14 9:57
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Accessors(fluent = true)
public class RandomStringStrategy implements GenerateStrategy {

    // 使用配置文件的方式解耦
    //@Value("${short.url.string.pool}")
    private String stringPool = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";

    // 首先对于StringPool 字符串池种的字符进行混淆后，进行随机
    private Integer shuffleNum = 5;

    // 是否选择需要进行shuffle
    private Boolean enableShuffle = true;

    public static void main(String[] args) {

        new RandomStringStrategy().provideShortUrl(null);

    }


    /**
     * @methodName : shuffle()
     * @author : HK意境
     * @date : 2022/4/18 18:01
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    private void shuffleStringPool(){
        String[] strPool = this.stringPool.split("");
        List<String> stringList = Arrays.asList(strPool);

        for (Integer i = 0; i < this.shuffleNum; i++) {
            Collections.shuffle(stringList);
        }

        StringBuilder sb = new StringBuilder();
        stringList.forEach(sb::append);

        this.stringPool = sb.toString();
    }


    /**
     * @methodName : 提供短链接的入口程序
     * @author : HK意境
     * @date : 2022/4/18 18:33
     * @description :
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public String provideShortUrl(Generator builder) {
        // 先进行扰乱
        if(this.enableShuffle){
            shuffleStringPool();
        }

        // 开始进行随机生成
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < builder.getLength(); i++) {
            sb.append(this.stringPool.charAt(random.nextInt(this.stringPool.length())));
        }
        return sb.toString();
    }
}