package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.hk.surl.api.core.IShortUrlService;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.web.aop.SysLog;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.core.common.util.DateTimeUtil;
import com.hk.surl.domain.entity.ShortUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName : ShortUrlController
 * @author : HK意境
 * @date : 2022/5/26 15:36
 * @description :短链接表 前端控制器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@RestController
@RequestMapping("/short-url")
public class ShortUrlController {

    @Autowired
    private IShortUrlService shortUrlService ;

    /**
     * @methodName : newShortUrlByLongUrl
     * @author : HK意境
     * @date : 2022/6/11 16:19
     * @description : 通过 外部传入的 长链接字符串，生成对应的短链接对象字符串,设置过期策略，生成对应的长链接对象，长短链接映射关系
     * @Todo :
     * @apiNote 通过长链接字符串，生成对应的短链接对象,设置过期策略，生成对应的长链接对象，长短链接映射关系
     * @params :
         * @param longUrl 长链接字符串
         * @param time 时间度量,默认-1
         * @param timeUnit 时间单位，默认day
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/new")
    public ResponseResult<ShortUrlVo> newShortUrlByLongUrl(@RequestParam(name = "longUrl", required = true) String longUrl, @RequestParam(name = "time" ,defaultValue = "-1",required = false) Integer time ,
                                                         @RequestParam(name = "timeUnit" ,defaultValue ="day", required = false)String timeUnit) throws ExecutionException, InterruptedException {

        // 构造过期时间
        LocalDateTime expirationTime = DateTimeUtil.getExpirationTimeByTimeEntry(time, timeUnit);

        // 如果数据库不存在长链接字符串对应的短链接那就生成，如果存在那就直接返回存在的数据对象
        Map.Entry<ShortUrl, AnonymousUser> entry = shortUrlService.newShortUrl(longUrl, expirationTime);

        // 封装ShortUrlVo 对象
        ShortUrlVo shortUrlVo = new ShortUrlVo(entry.getKey(), entry.getValue());

        return ResponseResult.SUCCESS().setData(shortUrlVo);
    }


    /**
     * @methodName newShortUrlSimpleByLongUrl
     * @author : HK意境
     * @date : 2022/6/30 10:03
     * @description :
     * @Todo :
     * @apiNote 简单简化的生产短链接
     * @params :
         * @param longUrl 长链接
         * @param expirationTime 过期时间
     * @return ResponseResult<ShortUrlVo>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/simple/new")
    public ResponseResult<ShortUrlVo> newShortUrlSimpleByLongUrl(@RequestParam(name = "longUrl", required = true) String longUrl,
                                                                 @RequestParam(name = "expirationTime")String expirationTime) throws ExecutionException, InterruptedException {
        // 构造过期时间
        LocalDate localDate = LocalDate.parse(expirationTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime expirationDate = LocalDateTime.of(localDate, LocalTime.MIN);

        // 如果数据库不存在长链接字符串对应的短链接那就生成，如果存在那就直接返回存在的数据对象
        Map.Entry<ShortUrl, AnonymousUser> entry = shortUrlService.newShortUrl(longUrl, expirationDate);

        // 封装ShortUrlVo 对象
        ShortUrlVo shortUrlVo = new ShortUrlVo(entry.getKey(), entry.getValue());

        return ResponseResult.SUCCESS().setData(shortUrlVo);
    }


    /**
     * @methodName : ResponseResult
     * @author : HK意境
     * @date : 2022/6/9 21:41
     * @description :
     * @Todo :
     * @apiNote 根据短链接字符串 进行查询
     * @params :
         * @param shortUrl 短链接字符串
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/get/surl")
    public ResponseResult<ShortUrl> getByShortUrl(@RequestParam(name = "shortUrl") String shortUrl) throws MalformedURLException {

        // 获取 uri 部分
        //shortUrl = ParseURLPairUtil.getUri(shortUrl);

        // 可见性，逻辑删除性
        LambdaQueryChainWrapper<ShortUrl> wrapper = shortUrlService.lambdaQuery().eq(ShortUrl::getVisible, true).eq(ShortUrl::getDeleted, false);

        // 根据 短链接字符串 查询
        ShortUrl one = shortUrlService.getOne(wrapper.eq(ShortUrl::getShortUrl, shortUrl));

        return ResponseResult.SUCCESS().setData(one);

    }


    /**
     * @methodName : getAllShortUrl
     * @author : HK意境
     * @date : 2022/6/9 21:24
     * @description :
     * @Todo :
     * @apiNote 获取全部 短链接对象列表，可选是否可见，过期，删除的
     * @params :
         * @param visible 是否查询不可见的短链接
         * @param deleted 是否查询逻辑删除的短链接
         * @param visible 是否查询过期的短链接
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "查看全部短链接",method = "getAllShortUrl",operate = "查询全部")
    @GetMapping("/get/all")
    public ResponseResult getAllShortUrl(@RequestParam(name = "visible", defaultValue = "false", required = false)Boolean visible,
                                         @RequestParam(name = "deleted", defaultValue = "false", required = false)Boolean deleted,
                                         @RequestParam(name = "expired", defaultValue = "false", required = false)Boolean expired){
        // 处理请求参数
        LambdaUpdateChainWrapper<ShortUrl> wrapper = shortUrlService.lambdaUpdate()
                .eq(!visible,ShortUrl::getVisible, true);

        // shortUrlService.getAllShortUrlByVisible();

        // 是否查询不可见的短链接
        /*if (!visible){
            // 不查询
            wrapper.eq(ShortUrl::getVisible,true);
        }
        // 是否查询逻辑删除的短链接
        if (!deleted){
            // 不查询
            wrapper.eq(ShortUrl::getDeleted,false);
        }
        // 是否查询过期的短链接
        if (!expired){
            // 不查询: 只查询过期时间大于当前时间的
            wrapper.ge(ShortUrl::getExpirationTime , LocalDateTime.now());
        }*/

        // 查询
        List<ShortUrl> shortUrlList = shortUrlService.list();

        return ResponseResult.SUCCESS().setData(shortUrlList);
    }


    /**
     * @methodName : getById
     * @author : HK意境
     * @date : 2022/6/9 20:50
     * @description :
     * @Todo :
     * @apiNote 根据 shortUrl 对象id 值查询
     * @params :
         * @param id shortUrl 对象id值
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/get/{id}")
    public ResponseResult<ShortUrl> getById(@PathVariable(value = "id", required = true) String id){

        // 根据 id  查询
        ShortUrl shortUrl = shortUrlService.getById(id);

        return new ResponseResult<ShortUrl>(ResultCode.SUCCESS,shortUrl);

    }


    /**
     * @methodName :
     * @author : HK意境
     * @date : 2022/6/9 21:00
     * @description :
     * @Todo :
     * @apiNote 添加或者保存 shortUrl 对象, 返回成功保存后的对象和保存结果
     * @params :
         * @param shortUrl 带保存的短链接对象
     * @return ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/save")
    public ResponseResult<ShortUrl> saveShortUrl(@RequestBody ShortUrl shortUrl){
        // 保存对象
        boolean save = shortUrlService.saveOrUpdate(shortUrl);

        // 根据保存对象是否成功进行响应状态码的确定
        ResponseResult<ShortUrl> result = new ResponseResult<>(save);
        result.setData(shortUrl);

        return result;

    }

    /**
     * @methodName : updateById
     * @author : HK意境
     * @date : 2022/6/9 21:06
     * @description :
     * @Todo :
     * @apiNote 根据 id 值，更新 shortUrl 对象，返回受影响的行数
     * @params :
         * @param shortUrl 待更新的对象
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PutMapping("/update")
    public ResponseResult<ShortUrl> updateById(@RequestBody ShortUrl shortUrl){

        log.info("{}", shortUrl);

        // 根据 id 更新 shorUrl 对象
        boolean update = shortUrlService.update(shortUrl,
                shortUrlService.lambdaUpdate()
                        .eq(ShortUrl::getId, shortUrl.getId()).eq(ShortUrl::getVisible, true));

        // 响应更新结果
        ResponseResult<ShortUrl> result = new ResponseResult<>(true);
        result.setData(shortUrl);

        return result;
    }


    /**
     * @methodName : delete
     * @author : HK意境
     * @date : 2022/6/9 20:59
     * @description :
     * @Todo :
     * @apiNote 删除指定 shortUrl 对象，id必须值， 返回是否删除成功
     * @params :
         * @param shortUrl 待删除的短链接对象
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @DeleteMapping("/delete")
    public ResponseResult<Boolean> delete(@RequestBody ShortUrl shortUrl){
        // 根据 id 删除
        //boolean rm = shortUrlService.removeById(shortUrl.getId());
        Boolean rm = shortUrlService.removeShortUrl(shortUrl);
        return new ResponseResult<Boolean>(rm);
    }


    /**
     * @methodName : deleteById
     * @author : HK意境
     * @date : 2022/6/9 20:55
     * @description :
     * @Todo :
     * @apiNote 根据短链接对象id 删除
     * @params :
         * @param id shortUrl对象的id值
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult<Boolean> deleteById(@PathVariable(name = "id",required = true)String id){

        // 根据id ,删除对象，返回是否伤删除成功
        boolean rm = shortUrlService.removeShortUrlById(id);
        //boolean rm = shortUrlService.removeById(id);
        // 返回删除结果对象
        return new ResponseResult<Boolean>(rm);

    }






}
