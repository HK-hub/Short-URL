package com.hk.surl.service.core;

import com.hk.surl.api.core.IUrlMapService;
import com.hk.surl.domain.entity.LongUrl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * @author : HK意境
 * @ClassName : AccessService
 * @date : 2022/6/12 15:46
 * @description : 短链接访问服务
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class AccessService {

    // random 是线程安全的对象 : 底层内部使用了乐观锁的机制，原子操作，cas 方式
    private static Random random = new Random();

    @Resource
    private IUrlMapService urlMapService ;


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/12 15:48
     * @description : 根据短链接字符串，跳转到对应的长链接地址，如果有多个，按照权重进行跳转
     * @Todo :
     * @apiNote 根据短链接字符串，跳转到对应的长链接地址，如果有多个，按照权重进行跳转
     * @params
         * @param shortUrl 完整的短链接字符串
     * @return LongUrl
     * @throws:
     * @Bug : 后期有待完善跳转策略：轮询，随机，权重
     * @Modified :
     * @Version : 1.0.0
     */
    public LongUrl getRedirectLongUrl(String shortUrl) {

        // 获取没有过期，可见的，未删除的短链接对应的长链接对象集合
        List<LongUrl> longUrlList = urlMapService.getLongUrlListByShortUrl(shortUrl);

        // 根据权重进行跳转
        return longUrlList.get(random.nextInt(longUrlList.size()));
    }


}
