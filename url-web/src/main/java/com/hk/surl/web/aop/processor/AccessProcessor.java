package com.hk.surl.web.aop.processor;


import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.common.util.IPUtil;
import com.hk.surl.common.util.TranceIdUtil;
import com.hk.surl.domain.entity.VisitLog;
import com.hk.surl.domain.mapper.VisitLogMapper;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.core.AsyncTaskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : AccessProcessor
 * @date : 2022/6/14 22:53
 * @description : 短链接访问日志记录
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Component
public class AccessProcessor extends LogProcessor{

    @Resource
    private AsyncTaskService taskService ;
    @Resource
    private VisitLogMapper visitLogMapper ;

    @Override
    public Object process(ProceedingJoinPoint point,ShortUrlVo vo) throws Throwable {

        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // SystemLog
        VisitLog visitLog = this.initSysLog(point, request, vo);

        // 入库
        Boolean aBoolean = taskService.writeAccessLogToDatabase(visitLog).get();
        return new ResponseResult<ShortUrlVo>(ResultCode.SUCCESS, vo);
    }

    @Override
    public Object after(Object result) {
        return null;
    }

    // 初始化 系统日志
    private VisitLog initSysLog(ProceedingJoinPoint point, HttpServletRequest request, ShortUrlVo vo) {
        // parse method, 获取日志注解对象
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();

        // 填充属性
        VisitLog visitLog = new VisitLog();
        try{
            visitLog.setShortUrl(vo.getShortUrl());
            visitLog.setLongUrl(vo.getLongUrl());
            visitLog.setCreateTime(LocalDateTime.now());
            visitLog.setEquipment(IPUtil.getUserAgent(request.getHeader("User-Agent")).toString());
            visitLog.setId(TranceIdUtil.getTraceId());
            visitLog.setMethod(request.getMethod());
            visitLog.setRequestHost(request.getRemoteHost());
            visitLog.setTargetHost(InetAddress.getLocalHost().getHostAddress());
            visitLog.setVisitorIp(IPUtil.getIpAddress(request));
            visitLog.setVisitorArea(IPUtil.getCityInfo(visitLog.getVisitorIp()));
            visitLog.setUpdateTime(visitLog.getCreateTime());


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            return visitLog ;
        }
    }


}
