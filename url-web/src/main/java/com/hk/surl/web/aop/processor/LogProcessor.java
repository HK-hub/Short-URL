package com.hk.surl.web.aop.processor;

import com.hk.surl.domain.entity.ShortUrl;
import com.hk.surl.domain.vo.ShortUrlVo;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author : HK意境
 * @ClassName : LogProcessor
 * @date : 2022/6/14 22:47
 * @description : 日志处理抽象类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public abstract class LogProcessor {

    public abstract Object process(ProceedingJoinPoint point, ShortUrlVo vo) throws Throwable;

    public abstract Object after(Object result);

}
