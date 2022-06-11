package com.hk.surl.core.provider.distributed;

import com.hk.surl.core.common.util.IdWorker;
import com.hk.surl.core.provider.GenerateProvider;
import com.hk.surl.core.generator.ShortUrlGenerator;
import lombok.Data;

/**
 * @author : HK意境
 * @ClassName : SnowFlakeProvider
 * @date : 2022/4/18 19:57
 * @description : 雪花算法生成策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class SnowFlakeProvider implements GenerateProvider {

    public static IdWorker idWorker = new IdWorker();

    // 最小生产长度, 中间生产长度，最大生产长度
    public static final int MIN_LENGTH = 6 ;
    private static final int NORMAL_LENGTH = 11;
    private static final int MID_LENGTH = 15;
    private static final int MAX_LENGTH = 19;


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/4/18 20:06
     * @description : 最后一共生成 12位=起始4位+中间4位+结尾4位
     *                      B站的所有视频目前都是用 12 位的短链接进行标记的
     * @Todo : 修改配置以及约束，使得能够根据指定的长度进行动态的修改
     *              //将id翻转：我们发现id很长，且高位很长部分是一样的数
     *
     * @params :
         * @param :
     * @return : String 经过运算切割拼装后的 短链接字符串
     * @throws:
     * @Bug : 目前不能根据长链接longUrl 的长度或者是生成器指定的 length 长度进行合适的运算，
     *              12 位：生产12 位长度的短链接再 10000 个以内的效果较为良好，超过 10000 个后冲突率开始急剧增加，所以12 位的这种方式适合于每天不超过 10000 个的短链接生产，并且只能当天有效，第二天就会失效的应用场景(笔试，面试url 链接)
     *
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public String provideShortUrl(ShortUrlGenerator generator) {

        // 因为雪花算法获取到的分布式ID 是连续的，存在生成后的短链接不安全的问题，所以需要更具加密策略进行选择
        String shortUrl = SnowFlakeProvider.createWithSnowFlake(generator.length());

        // 如果采用了 加密算法，在外部进行加密
        // 在外部使用加密工具进行加密

        return shortUrl ;
    }


    /**
     * @methodName : createWithSnowFlake
     * @author : HK意境
     * @date : 2022/5/29 14:25
     * @description : 根据分布式ID生成算法(雪花算法) 产生 短链接url(32位长度字符串) ,然后根据指定的长度进行切割
     *                     - 这里因为 idworker 产生的 是一个 long 数值，具有32 位，所以我们选择头四位，中间四位，尾部四位来进行拼接出一个12 位的短链接
     *                     - 还有一种方案就是：length<12 生成12 位，length > 12 生成 16位
     *
     *      <img src="https://img-blog.csdnimg.cn/img_convert/5ba368daed80d653109e77940df2d255.webp?x-oss-process=image/format,png" />
     * @Todo :
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :       //得到id 很长且高位很长部分是一样的数
     *              //将id翻转：我们发现id很长，且高位很长部分是一样的数
     * @Modified :  这是第一个版本的分布式ID 在 6位模式下生产的 短链接：在生成个数 > 10000 个后冲突率会进行急剧增加，所以放弃
     *                 String head = url.substring(0,4);
     *                 String body = url.substring((url.length() / 2) - 2, (url.length() / 2) + 2);
     *                 String tail = url.substring(url.length() - 4);
     *                 url = head+body+tail ;
     *              修改后使用 reverse + 去除高位，将冲突率从 0.8+ 降到了0.3+
     * @Version : 1.0.0
     */
    public static String createWithSnowFlake(int length){

        // 转换合适长度
        // 除 最小长度适合的场景外，最小程度是一个冲突率非常高的生成算法，不建议选择
        // 建议选择 9， 12，最大这种长度：因为id 高位很长都是不变的，所以reverse 后 去除高位较为合适
        if (length <= SnowFlakeProvider.MIN_LENGTH){
            // 最小长度
            length = SnowFlakeProvider.MIN_LENGTH;
        }else if (length <= SnowFlakeProvider.NORMAL_LENGTH){
            // 一般长度
            length = SnowFlakeProvider.NORMAL_LENGTH;
        }else if (length <=SnowFlakeProvider.MID_LENGTH){
            // 中间长度
            length = SnowFlakeProvider.MID_LENGTH ;
        }else{
            // 最大长度
            length = SnowFlakeProvider.MAX_LENGTH ;
        }

        // 获取待加工的 短链接url
        String url = String.valueOf(SnowFlakeProvider.idWorker.nextId());

        // reverse 后，根据长度进行去除高位
        StringBuilder urlSb = new StringBuilder(url);
        url = urlSb.reverse().toString();

        // 去除高位: 需要去除的位数=数值总长度-保留位数
        url = url.substring(0,length);

        return url ;
    }




    public static void main(String[] args) {

        SnowFlakeProvider snowFlakeProvider = new SnowFlakeProvider();

        for (int i = 0; i < 10; i++) {
            String url = ""+ SnowFlakeProvider.idWorker.nextId();
            System.out.println();
            String head = url.substring(0,4);
            String body = url.substring((url.length() / 2) - 2, (url.length() / 2) + 2);
            String tail = url.substring(url.length() - 4);
            url = head+body+tail ;
            System.out.println(url);
        }
    }

}
