package com.hk.surl.core.strategy.generate;

import com.hk.surl.core.common.IdWorker;
import com.hk.surl.core.strategy.GenerateStrategy;
import com.hk.surl.core.strategy.Generator;
import com.hk.surl.core.strategy.enums.EncryptStrategy;
import lombok.Data;

/**
 * @author : HK意境
 * @ClassName : SnowFlakeStrategy
 * @date : 2022/4/18 19:57
 * @description : 雪花算法生成策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class SnowFlakeStrategy implements GenerateStrategy {

    private IdWorker idWorker = new IdWorker();


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/4/18 20:06
     * @description : 最后一共生成 12位=起始4位+中间4位+结尾4位
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
    public String provideShortUrl(Generator generator) {

        // 产生分布式ID
        String url = String.valueOf(idWorker.nextId());

        // 获取标识字符串

        // 因为雪花算法获取到的分布式ID 是连续的，存在生成后的短链接不安全的问题，所以需要更具加密策略进行选择
        if(EncryptStrategy.NONE == generator.getEncryptStrategy()){
            // 没有指定加密算法，需要进行切割
            String head = url.substring(0,4);
            String body = url.substring((url.length() / 2) - 2, (url.length() / 2) + 2);
            String tail = url.substring(url.length() - 4);
            url = head+body+tail ;
        }
        // 如果采用了 加密算法，在外部进行加密

        return url ;
    }


    public static void main(String[] args) {

        SnowFlakeStrategy snowFlakeStrategy = new SnowFlakeStrategy();

        for (int i = 0; i < 10; i++) {
            String url = ""+snowFlakeStrategy.getIdWorker().nextId();
            System.out.println();
            String head = url.substring(0,4);
            String body = url.substring((url.length() / 2) - 2, (url.length() / 2) + 2);
            String tail = url.substring(url.length() - 4);
            url = head+body+tail ;
            System.out.println(url);
        }


    }

}
