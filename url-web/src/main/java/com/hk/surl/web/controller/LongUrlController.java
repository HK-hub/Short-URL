package com.hk.surl.web.controller;


import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.hk.surl.api.core.ILongUrlService;
import com.hk.surl.common.log.SysLog;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.common.util.IPUtil;
import com.hk.surl.domain.entity.LongUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName : LongUrlController
 * @author : HK意境
 * @date : 2022/5/26 15:35
 * @description :长链接 前端控制器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@RestController
@RequestMapping("/long-url")
public class LongUrlController {

    // 注入service 服务
    @Autowired
    private ILongUrlService longUrlService ;


    /**
     * @methodName : getLongUrlByLongUrlString
     * @author : HK意境
     * @date : 2022/6/9 20:30
     * @description :处理长链接(去除参数) 后根据长链接对象的 longUrl 字符串进行查找
     * @Todo :
     * @apiNote : 根据长链接对象的 longUrl 字符串进行查找
     * @params : 
         * @param : longUrlString 长链接字符串
     * @return : ResponseResult
     * @throws: 
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping("/get/lurl")
    public ResponseResult<LongUrl> getLongUrlByLongUrlString(String longUrlString){

        // 处理 长链接, 去除参数部分
        longUrlString = longUrlString.substring(0,longUrlString.indexOf("?"));

        // 构造查询条件，进行数据查询
        LambdaQueryChainWrapper<LongUrl> wrapper = longUrlService.lambdaQuery().eq(LongUrl::getUrl, longUrlString);
        LongUrl longUrl = longUrlService.getOne(wrapper);

        // 响应
        return ResponseResult.SUCCESS().setData(longUrl);
    }


    /**
     * @methodName : getAllLongUrl
     * @author : HK意境
     * @date : 2022/6/9 9:06
     * @description : 获取全部的 长链接对象，不包括过期的，对外不可见的，如果需要查询已经删除，过期的，对外不可见的，请使用请求参数来进行选择查询
     * @Todo :
     * @apiNote 获取全部的 长链接对象，可选对外不可见的，删除的
     * @params :
         * @param : visible 是否选择查询不可见的长链接
         * @param : deleted 是否选择逻辑删除的长链接
     * @return : ResponseResult
     * @throws : 
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @SysLog(businessType = "查询群不长连接对象",method = "getAllLongUrl",operate = "查询")
    @GetMapping("/get/all")
    public ResponseResult getAllLongUrl(@RequestParam(name = "visible", defaultValue = "false",required = false)Boolean visible,
                                        @RequestParam(name = "deleted", defaultValue = "false", required = false)Boolean deleted){

        LambdaQueryChainWrapper<LongUrl> wrapper = longUrlService.lambdaQuery()
                .eq(LongUrl::getVisible, true).eq(LongUrl::getDeleted, false);

        // 根据可见性，过期，删除 逻辑条件 来进行查询
        if (!visible) {
            // 不选择对外不可见的长链接，仅仅选择对外可见的
            wrapper.eq(LongUrl::getVisible,true);
        }
        // 不选择删除的长链接，仅仅选择未删除的
        if (!deleted){
            wrapper.eq(LongUrl::getDeleted,false);
        }
        
        // 查询所有
        //List<LongUrl> longUrlList = longUrlService.list(wrapper);
        List<LongUrl> longUrlList = longUrlService.list();

        return ResponseResult.SUCCESS().setData(longUrlList);
    }

    /**
     * @methodName : getLongUrlById
     * @author : HK意境
     * @date : 2022/6/9 8:53
     * @description : 通过 id 查询长链接，返回查询的结果对象
     * @Todo :
     * @apiNote 通过 id 查询长链接
     * @params :
         * @param : id 长链接id
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping(value = "/get/{id}")
    public ResponseResult<LongUrl> getLongUrlById(@PathVariable(name = "id")String id ){

        // 通过 id 查询 长链接对象
        LongUrl longUrl = longUrlService.getById(id);
        // 假设对象存在查询对象成功
        ResponseResult<LongUrl> result = new ResponseResult<>(ResultCode.SUCCESS,longUrl);

        if (longUrl == null) {
            // 对象不存在
            result.setResultCode(ResultCode.FAIL);
        }
        return result;
    }

    /**
     * @methodName : saveLongUrl
     * @author : HK意境
     * @date : 2022/6/8 23:45
     * @description :
     * @Todo : 保存一个长链接实体
     * @apiNote 保存/新增/添加一个 longUrl 长链接
     * @params :
         * @param : longUrl 长链接对象
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PostMapping(value = "/save")
    public ResponseResult saveLongUrl(@RequestBody LongUrl longUrl){
        // 保存长链接对象，设置属性值，返回是保存成功
        boolean saveRes = longUrlService.save(longUrl);

        return new ResponseResult<LongUrl>(saveRes).setData(longUrl);
    }


    /**
     * @methodName : deleteLongUrl
     * @author : HK意境
     * @date : 2022/6/9 0:03
     * @description : // 删除长链接对象，删除成功返回 true，并且把删除对象返回
     * @Todo :
     * @apiNote 根据 longUrl 的id 删除长链接对象
     * @params :
         * @param : longUrl 长链接实体
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @DeleteMapping("/delete")
    public ResponseResult deleteLongUrl(@RequestBody LongUrl longUrl){
        // 删除长链接对象，删除成功返回 true，并且把删除对象返回
        boolean rmRes = longUrlService.removeById(longUrl.getId());

        return new ResponseResult<LongUrl>(rmRes).setData(longUrl);
    }


    /**
     * @methodName : deleteLongUrlById
     * @author : HK意境
     * @date : 2022/6/9 0:03
     * @description : // 删除长链接对象，删除成功返回 true，并且把删除对象返回
     * @Todo :
     * @apiNote 根据 longUrl 的id 删除长链接对象
     * @params :
     * @param : id 长链接实体的id 值
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteLongUrlById(@PathVariable(name = "id") String id){
        // 删除长链接对象，删除成功返回 true，并且把删除对象返回
        boolean rmRes = longUrlService.removeById(id);

        return new ResponseResult<Boolean>(rmRes).setData(rmRes);
    }


    /**
     * @methodName : updateLongUrl
     * @author : HK意境
     * @date : 2022/6/9 0:10
     * @description :
     * @Todo :
     * @apiNote 根据 长链接id ,修改对象， 返回成功修改后的对象
     * @params :
         * @param : longUrl 长链接对象
     * @return : ResponseResult
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @PutMapping("/update")
    public ResponseResult updateLongUrlById(@RequestBody LongUrl longUrl){
        // 根据id 更新
        boolean udRes = longUrlService.updateById(longUrl);

        return new ResponseResult<LongUrl>(udRes).setData(longUrl);
    }






}
