package com.hk.surl.web.aop.processor;


import cn.hutool.json.JSONUtil;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.web.aop.SysLog;
import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.common.util.IPUtil;
import com.hk.surl.common.util.TranceIdUtil;
import com.hk.surl.domain.entity.LogTrance;
import com.hk.surl.service.util.AsyncTaskService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author : HK意境
 * @ClassName : DefaultProcessor
 * @date : 2022/6/14 22:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Component
public class DefaultProcessor extends LogProcessor {

    @Resource
    private AsyncTaskService taskService ;

    @Override
    public Object process(ProceedingJoinPoint point , ShortUrlVo vo) throws Throwable {

        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 返回结果
        Object result ;
        // 记录开始时间
        long beginTime = System.nanoTime();

        // SystemLog
        LogTrance logTrance = this.initSysLog(point, request);

        // 执行目标方法
        try {
            // 执行目标方法获取结果
            result = point.proceed();
        } catch (Throwable throwable) {
            // 执行时间
            logTrance.setExecuteTime(String.valueOf(this.doGetExecuteTime(beginTime)));
            // 处理结果：异常或错误
            logTrance.setResult("exception or error");
            // 从异常message或堆栈里获取异常信息
            logTrance.setMsg(throwable.getMessage());
            logTrance.setCode(ResultCode.FAIL.code());
            // 入库
            taskService.writeLogToDatabase(logTrance);

            throw throwable;
        }
        // 执行时间
        logTrance.setExecuteTime(String.valueOf(this.doGetExecuteTime(beginTime)));
        // 执行结果
        logTrance.setResult(result.getClass().getName());
        // 响应结果
        logTrance.setCode(((ResponseResult) result).getCode());

        // 入库
        taskService.writeLogToDatabase(logTrance);

        return result ;

    }

    @Override
    public Object after(Object result) {

        return null ;
    }


    // 初始化 系统日志
    private LogTrance initSysLog(ProceedingJoinPoint point, HttpServletRequest request) {
        // parse method, 获取日志注解对象
        MethodSignature signature = (MethodSignature)point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = method.getAnnotation(SysLog.class);

        // 填充属性
        LogTrance logTrance = new LogTrance();
        try{
            logTrance.setTranceId(TranceIdUtil.getTraceId());
            logTrance.setBusinessMethod(method.getName());
            logTrance.setCreateTime(LocalDateTime.now());
            logTrance.setBusinessType(sysLog.businessType());
            logTrance.setOperate(sysLog.operate());
            logTrance.setLevel(sysLog.level());
            logTrance.setParameters(JSONUtil.toJsonStr(point.getArgs()));
            logTrance.setPath(request.getRequestURI());
            logTrance.setRequestMethod(request.getMethod());
            logTrance.setIpAddress(IPUtil.getIpAddress(request));
            logTrance.setUserAgent(IPUtil.getUserAgent(request.getHeader("User-Agent")).toString());
            logTrance.setLocation(IPUtil.getCityInfo(logTrance.getIpAddress()));

        }catch(Exception e){
            e.printStackTrace();

        }finally {
            return logTrance ;
        }

    }


    // 计算接口方法执行时间
    public long doGetExecuteTime(long beginTime){
        return (System.nanoTime() - beginTime) / 1000000;
    }


}
