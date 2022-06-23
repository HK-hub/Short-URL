package com.hk.surl.web.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : HK意境
 * @ClassName : LoginInterceptor
 * @date : 2022/6/19 14:08
 * @description : 登录用户空间拦截器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    // access_token 请求头中的 访问凭证名称
    private static String ACCESS_TOKEN = "secretKey" ;

    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 查看是否存在 短链接和 短链接密钥(RSA 派发的密钥)
        String secretKey = request.getHeader(ACCESS_TOKEN);

        // 先在redis 缓存里面查询
        stringRedisTemplate.opsForValue();

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
