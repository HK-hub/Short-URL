package com.hk.surl.service.core;


import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hk.surl.api.core.IShortUrlService;
import com.hk.surl.core.generator.template.DefaultShortUrlGenerator;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.domain.entity.UrlMap;
import com.hk.surl.domain.mapper.LongUrlMapper;
import com.hk.surl.domain.mapper.ShortUrlMapper;
import com.hk.surl.domain.mapper.UrlMapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName : ShortUrlServiceImpl
 * @author : HK意境
 * @date : 2022/5/26 15:26
 * @description :短链接表 服务实现类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper, ShortUrl> implements IShortUrlService {

    // 自动注入 默认 短链接生成器
    @Resource
    private DefaultShortUrlGenerator shortUrlGenerator;
    @Resource
    private AsyncTaskService asyncTaskService ;
    @Resource
    private LongUrlMapper longUrlMapper ;
    @Resource
    private ShortUrlMapper shortUrlMapper ;
    @Resource
    private UrlMapMapper urlMapMapper ;


    /**
     * @methodName : newShortUrl
     * @author : HK意境
     * @date : 2022/6/10 21:44
     * @description : 根据 长链接字符串 对象, 调用生产工具，生成
     * @Todo : 短链接对象，长链接对象，映射对象-> 使用异步任务优化效率
     * @apiNote :
     * @params :
         * @param longUrlStr 长链接字符串
     * @param expirationTime 短链接对象过期时间
     * @return ShortUrl
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public ShortUrl newShortUrl(String longUrlStr, LocalDateTime expirationTime) throws ExecutionException, InterruptedException {

        // 返回对象
        ShortUrl shortUrl = null ;

        // 首先校验 数据库中是否存在 对应的 长链接对象
        LongUrl longUrl = longUrlMapper.selectOne(new LambdaQueryChainWrapper<>(longUrlMapper)
                .eq(LongUrl::getUrl, longUrlStr).eq(LongUrl::getVisible, true));
        if (longUrl == null){
            // 不存在
            shortUrl = this.doNewShortUrl(longUrlStr, expirationTime);

        }else{
            // 以及存在了长链接 对象，并且是可见的，返回对应的短链接对象
            shortUrl = shortUrlMapper.selectByLongUrl(longUrl);
        }

        return shortUrl ;
    }



    /**
     * @methodName : removeShortUrlById
     * @author : HK意境
     * @date : 2022/6/11 20:20
     * @description :  根据 短链接 id删除对象
     * @Todo : 删除短链接对象，长链接对象，映射对象
     * @apiNote : 异步删除
     * @params :
         * @param sid 短链接id
     * @return boolean
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    @Transactional
    public boolean removeShortUrlById(String sid) {

        // 删除 shortUrl 对象
        int shortRm = this.shortUrlMapper.deleteById(sid);
        // 查询出短链接对象对应的所有长链接id
        List<String> longIds = urlMapMapper.selectLongIdByShortId(sid);
        int longRm = this.longUrlMapper.deleteBatchIds(longIds);

        // 删除 映射对象
        int mapRm = urlMapMapper.deleteById(sid);

        // 如果都能偶成功删除，则认为删除成功
        return shortRm >0 && longRm > 0 && mapRm > 0;
    }



    /**
     * @methodName : removeShortUrl
     * @author : HK意境
     * @date : 2022/6/12 13:14
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl 待删除的短链接对象
     * @return Boolean
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public Boolean removeShortUrl(ShortUrl shortUrl) {

        Boolean res = null ;

        // 判断 id 是否为空, 和 短链接字符串
        if (shortUrl.getId() != null) {
            res = this.removeShortUrlById(shortUrl.getId());
        }else{
            // 通过 短链接字符串删除
            res = this.removeShortUrlByUrl(shortUrl.getShortUrl());
        }




        return res;
    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/12 13:18
     * @description : 根据短链接字符串删除短链接对象，长链接对象，urlMap 映射对象
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrlStr 待删除短链接字符串
     * @return Boolean
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public Boolean removeShortUrlByUrl(String shortUrlStr){

        // 查询获取到短链接对应的 id 值
        ShortUrl shortUrl = shortUrlMapper.selectByShortUrl(shortUrlStr);
        // 根据id 删除
        boolean res = this.removeShortUrlById(shortUrl.getId());

        return res;
    }


    /**
     * @methodName : doNewShortUrl
     * @author : HK意境
     * @date : 2022/6/11 14:52
     * @description : 执行生成新的 短链接
     * @Todo :
     * @apiNote :
     * @params :
         * @param longUrlStr 长链接字符串
     * @param expirationTime 短链接过期时间
     * @return ShortUrl 生成后的短链接对象
     * @throws:
     * @Bug : 问题很大， 三个数据的插入不是原子性的
     * @Modified :
     * @Version : 1.0.0
     */
    @Transactional
    public ShortUrl doNewShortUrl(String longUrlStr, LocalDateTime expirationTime) throws ExecutionException, InterruptedException {
        // 生产短链接对象
        ShortUrl shortUrl = this.shortUrlGenerator.generate(longUrlStr);
        shortUrl.setExpirationTime(expirationTime);
        // 使用异步任务插入保存短链接对象
        CompletableFuture<ShortUrl> shortUrlFuture = asyncTaskService.newAndSaveShortUrl(shortUrl);

        // 生产长链接对象
        CompletableFuture<LongUrl> longUrlFuture =  asyncTaskService.newAndSaveLongUrl(longUrlStr);

        // 获取异步任务执行结果
        shortUrl = shortUrlFuture.get();
        LongUrl longUrl  = longUrlFuture.get();
        // 异步插入 : 废弃
        // 这里不能使用异步插入，因为存在插入失败的情况，会存在数据库，长短链接关联数据不一致的情况
        //CompletableFuture<UrlMap> urlMapFuture = asyncTaskService.newAndSaveUrlMap(shortUrl, longUrl);

        // 必须使用同步插入

        if (shortUrl != null && longUrl != null){
            // 长短链接对象都不为空，插入数据库成功了
            // 构造映射对象
            UrlMap urlMap = new UrlMap(shortUrl, longUrl);
            urlMap.setExpirationTime(expirationTime);
            // 插入保存
            int insert = urlMapMapper.insert(urlMap);
        }


        return shortUrl;
    }


}
