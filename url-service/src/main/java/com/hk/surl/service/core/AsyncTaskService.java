package com.hk.surl.service.core;

import com.hk.surl.domain.entity.LogTrance;
import com.hk.surl.domain.entity.LongUrl;
import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.domain.mapper.*;
import com.hk.surl.util.LongUrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author : HK意境
 * @ClassName : AsyncTaskService
 * @date : 2022/6/11 13:36
 * @description : 异步任务服务入口
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Service
public class AsyncTaskService {

    @Resource
    private ShortUrlMapper shortUrlMapper ;
    @Resource
    private LongUrlMapper longUrlMapper ;
    @Resource
    private UrlMapMapper urlMapMapper ;
    @Resource
    private VisitLogMapper logMapper ;
    @Resource
    private LogTranceMapper logTranceMapper ;



    /**
     * @methodName :newAndSaveShortUrl
     * @author : HK意境
     * @date : 2022/6/11 13:47
     * @description : 使用异步任务插入短链接对象，
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl 待插入的 短链接对象
     * @return CompletableFuture<ShortUrl> 返回异步任务执行对象
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<ShortUrl> newAndSaveShortUrl(ShortUrl shortUrl){

        // 返回插入的行数
        int rows = shortUrlMapper.insert(shortUrl);

        // 断言
        //Assertions.assertThat(rows).isGreaterThanOrEqualTo(1).withFailMessage()

        if (rows > 0){
            // 插入成功
            return CompletableFuture.completedFuture(shortUrl);
        }else {
            // 插入失败
            return CompletableFuture.completedFuture(null);
        }
    }


    /**
     * @methodName : newAndSaveLongUrl
     * @author : HK意境
     * @date : 2022/6/11 14:29
     * @description : 通过 工具类 生成长链接对象，然后保存
     * @Todo :
     * @apiNote :
     * @params :
         * @param longUrlStr 长链接字符串
     * @return CompletableFuture<LongUrl> 生成，保存后的长链接对象
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<LongUrl> newAndSaveLongUrl(String longUrlStr) {
        // 生成对应的长链接对象
        LongUrl longUrl = LongUrlUtil.analyzeStringToLongUrl(longUrlStr);
        log.info("longUrl: {}", longUrl);

        // 校验
        if (longUrl != null) {
            // url 长链接合法, 保存
            int insert = longUrlMapper.insert(longUrl);
            if (insert <= 0){
                // 插入失败
                longUrl = null ;
            }
        }

        // 返回异步任务执行结果
        return CompletableFuture.completedFuture(longUrl);
    }


    /**
     * @methodName : newAndSaveUrlMap
     * @author : HK意境
     * @date : 2022/6/11 15:02
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param shortUrl 短链接对象
         * @param longUrl 长链接对象
     * @return CompletableFuture<UrlMap>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    /*@Async("asyncTaskExecutor")
    @Transactional
    public CompletableFuture<UrlMap> newAndSaveUrlMap(ShortUrl shortUrl, LongUrl longUrl) {

        if (shortUrl != null && longUrl != null){
            // 长短链接对象都不为空，插入数据库成功了
            // 构造映射对象
            UrlMap urlMap = new UrlMap(shortUrl, longUrl);
            // 插入保存
            int insert = urlMapMapper.insert(urlMap);
            if (insert > 0){
                return CompletableFuture.completedFuture(urlMap);
            }
        }
        return CompletableFuture.completedFuture(null);
    }*/


    /**
     * @methodName : writeLogToDatabase
     * @author : HK意境
     * @date : 2022/6/14 16:33
     * @description : 异步写入日志
     * @Todo :
     * @apiNote :
     * @params :
         * @param logTrance 日志追踪
     * @return CompletableFuture
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Async("asyncTaskExecutor")
    public CompletableFuture<Boolean> writeLogToDatabase(LogTrance logTrance){

        // 写入日志
        int insert = logTranceMapper.insert(logTrance);

        return CompletableFuture.completedFuture(insert>0);
    }


}
