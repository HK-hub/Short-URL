package com.hk.surl.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : HK意境
 * @ClassName : LogAndTranceInterceptor
 * @date : 2022/6/14 9:22
 * @description : 日志和链路拦截器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LogAndTranceInterceptor implements HandlerInterceptor {



    /**
     * @methodName : preHandle
     * @author : HK意境
     * @date : 2022/6/14 9:36
     * @description :
     * @Todo :
     * @apiNote :
     * @params :
         * @param request  请求
         * @param response 响应
         * @param handler
     * @return boolean 是否放行
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求路径，参数，用户浏览器等信息



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
