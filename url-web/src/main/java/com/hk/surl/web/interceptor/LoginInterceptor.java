package com.hk.surl.web.interceptor;

import com.hk.surl.common.exception.BaseException;
import com.hk.surl.common.response.ResultCode;
import com.hk.surl.domain.entity.AnonymousUser;
import com.hk.surl.web.exception.UrlWebException;
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
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 查看是否存在 短链接和 短链接密钥(RSA 派发的密钥)
        String secretKey = request.getHeader(ACCESS_TOKEN);

        // 请求头是否包含安全码
        if (secretKey == null || "".equals(secretKey)){
            // 没有授予安全码，未认证
            throw new UrlWebException(ResultCode.UNAUTHENTICATED);
        }

        // 校验安全码是否正确：先在redis 缓存里面查询
        AnonymousUser user = (AnonymousUser) redisTemplate.opsForValue().get(secretKey);

        if (user == null){
            // 用户登录已过期
            throw new UrlWebException(ResultCode.ACCESS_TOKEN_EXPIRED);
        }

        // 已经认证可以继续访问
        return true;
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
