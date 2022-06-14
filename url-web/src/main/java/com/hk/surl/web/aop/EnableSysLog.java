package com.hk.surl.web.aop;


import java.lang.annotation.*;

/**
 * @Description: 启用系统日志
 *
 * @author: binghua.zheng
 * @Date: 2021/11/12 23:01
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(AopAspectConfig.class)
public @interface EnableSysLog {
}
