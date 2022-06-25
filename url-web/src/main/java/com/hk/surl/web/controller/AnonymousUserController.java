package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.IAnonymousUserService;
import com.hk.surl.api.core.IVisitLogService;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.service.core.AnonymousUserServiceImpl;
import com.hk.surl.web.aop.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : AnonymousUserController
 * @author : HK意境
 * @date : 2022/5/26 15:32
 * @description :匿名用户，临时用户 前端控制器
 * @Todo : 这里需要用来用户短链接数据的认证, 用户短链接数据的获取
 *
 * @Bug : 对于所有的短链接，都使用一个 secret_key 进行校验
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/anonymous")
public class AnonymousUserController {

    @Resource
    private IVisitLogService visitLogService ;

    @Resource
    private IAnonymousUserService anonymousUserService ;

    /**
     * @methodName :login
     * @author : HK意境
     * @date : 2022/6/22 0:44
     * @description : 匿名用户使用 短链接 和 secret_key 进行登录
     * @Todo : 后续查看所有数据都需要在请求头中带上 secret_key
     * @apiNote 匿名用户使用 短链接 和 secret_key 进行登录
     * @params :
         * @param shortUrl 短链接
         * @param secretKey 安全密钥
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping(value = "/login")
    public ResponseResult<AnonymousUser> login( String shortUrl, String secretKey){

        // 参数校验
        //log.info("{},{}",shortUrl,secretKey);

        // 如果存在 secret_key 则生成token
        AnonymousUser user = anonymousUserService.anonymousLogin(shortUrl, secretKey);

        // 没有此短链接，或者安全密钥错误
        ResponseResult<AnonymousUser> result = new ResponseResult<>(ResultCode.USER_LOGIN_ERROR);
        result.setData(user);
        if (user != null) {
            result.setResultCode(ResultCode.SUCCESS);
        }

        return result;
    }



    /**
     * @methodName : getShortUrlTodayAccessCount
     * @author : HK意境
     * @date : 2022/6/23 23:59
     * @description :
     * @Todo :
     * @apiNote 查询短链接今日的访问总量
     * @params :
         * @param shortUrl 短链接字符串
     * @return ResponseResult<List<VisitLog>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "查询短链接今日访问量", operate = "查询")
    @GetMapping("/today/access/count")
    public ResponseResult<List<VisitLog>> getShortUrlTodayAccessCount(@RequestParam(name = "shortUrl")String shortUrl){

        // 构造开始时间，结束时间为今日
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        List<VisitLog> logList = visitLogService.getListByDateTime(shortUrl, startTime, endTime);

        return new ResponseResult<>(ResultCode.SUCCESS, logList);
    }


    /**
     * @methodName :getShortUrlTodayNewAccess
     * @author : HK意境
     * @date : 2022/6/24 19:47
     * @description :
     * @Todo :
     * @apiNote 查询今日新增访问数据
     * @params :
         * @param shortUrl 指定短链接
     * @return ResponseResult<List<VisitLog>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/today/access/new/visit")
    public ResponseResult<List<VisitLog>> getShortUrlTodayNewAccess(@RequestParam(name = "shortUrl")String shortUrl){

        // 查询今日新增访问数据
        List<VisitLog> newVisitors = this.anonymousUserService.getTodayNewAccessVisitData(shortUrl);

        return new ResponseResult<>(ResultCode.SUCCESS, newVisitors);
    }


    /**
     * @methodName : getTodayNewAccessVisitor
     * @author : HK意境
     * @date : 2022/6/24 21:01
     * @description :
     * @Todo :
     * @apiNote 获取今日新增独立IP 访问用户
     * @params :
         * @param shortUrl 指定短链接字符串
     * @return ResponseResult<List<VisitLog>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/today/access/new/alone")
    public ResponseResult<List<VisitLog>> getTodayNewAccessVisitor(@RequestParam(name = "shortUrl")String shortUrl){

        // 获取今日访问的独立IP 集合
        List<VisitLog> aloneIps = this.anonymousUserService.getTodayNewAccessVisitor(shortUrl);

        return new ResponseResult<>(ResultCode.SUCCESS, aloneIps);
    }


    /**
     * @methodName :getTotalAccessData
     * @author : HK意境
     * @date : 2022/6/24 21:05
     * @description :
     * @Todo :
     * @apiNote 获取创建以来的全部访问数据
     * @params :
         * @param shortUrl 指定短链接
     * @return ResponseResult<List<VisitLog>>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/total/access/data")
    public ResponseResult<List<VisitLog>> getTotalAccessData(@RequestParam(name = "shortUrl")String shortUrl){

        // 构造查询条件
        LambdaQueryChainWrapper<VisitLog> wrapper = this.visitLogService.lambdaQuery().eq(VisitLog::getShortUrl, shortUrl);
        // 查询获取数据
        List<VisitLog> visitLogList = this.visitLogService.list(wrapper);

        // 响应数据
        return new ResponseResult<>(ResultCode.SUCCESS, visitLogList);

    }


    // 查询一共的访问数据中的独立IP用户数量
    /**
     * @methodName : getTotalAccessAloneUser
     * @author : HK意境
     * @date : 2022/6/24 21:37
     * @description :
     * @Todo :
     * @apiNote 获取所有独立IP用户的访问记录数据
     * @params :
         * @param shortUrl 短链接
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/total/access/alone")
    public ResponseResult<List<Map.Entry<String, Integer>>> getTotalAccessAloneUser(@RequestParam(name = "shortUrl")String shortUrl){




        return new ResponseResult<>(ResultCode.SUCCESS);
    }



}
