package com.hk.surl.common.log;

/**
 * @author : HK意境
 * @ClassName : SysLog
 * @date : 2022/6/14 10:09
 * @description : 系统日志注解
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /**
     *  业务类型
     *
     * @return string
     */
    String businessType() default "";

    /**
     * 方法说明
     *
     * @return string
     */
    String method() default "";

    /**
     * 执行的操作
     *
     */
    String operate() default "" ;

    /**
     * 日志级别 1-重要; 2-普通
     *
     * @return string
     */
    String level() default "2";


}
