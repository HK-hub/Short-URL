package com.hk.surl.web.aop;

import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.domain.vo.ShortUrlVo;
import com.hk.surl.service.core.AsyncTaskService;
import com.hk.surl.web.aop.processor.AccessProcessor;
import com.hk.surl.web.aop.processor.DefaultProcessor;
import com.hk.surl.web.aop.processor.LogProcessor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import javax.annotation.Resource;

/**
 * @author : HK意境
 * @ClassName : SysLogAspect
 * @date : 2022/6/14 10:15
 * @description : 系统日志切面
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@Aspect
public class SysLogAspect {

    @Resource
    private AsyncTaskService taskService ;
    @Resource
    private DefaultProcessor defaultProcessor;
    @Resource
    private AccessProcessor accessProcessor ;

    @Pointcut("@annotation(com.hk.surl.web.aop.SysLog)")
    public void pointcut(){ }


    /**
     * 拦截Controller的 @SysLog 注解，记录相关请求日志信息
     */
    @Around("pointcut()")
    public Object handleSysLogPoint(ProceedingJoinPoint point) throws Throwable {

        // 获取 处理器类型
        MethodSignature signature = (MethodSignature)point.getSignature();
        SysLog sysLog =  signature.getMethod().getAnnotation(SysLog.class);
        // 获取处理器类型
        Class<? extends LogProcessor> processor = sysLog.processor();

        // 定义返回结果
        Object result = null ;

        // 默认处理器, 首先进行默认处理
        result = this.defaultProcessor.process(point, null);

        // 判断执行结果是否正确
        if (processor == AccessProcessor.class){
            if (result instanceof ResponseResult){
                // 响应正确
                ResponseResult<ShortUrlVo> responseResult = (ResponseResult<ShortUrlVo>) result;
                result = this.accessProcessor.process(point, responseResult.getData());
            }
        }


        return result ;
    }

}
