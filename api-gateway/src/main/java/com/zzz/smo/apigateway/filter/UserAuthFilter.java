package com.zzz.smo.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zzz.smo.apigateway.constant.CookieConstant;
import com.zzz.smo.apigateway.constant.RedisConstant;
import com.zzz.smo.apigateway.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * @Author: Sean
 * @Date: 2019/1/13 0:50
 */
@Component
public class UserAuthFilter extends ZuulFilter {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (filterFlag(request))
            return true;
        return false;
    }

    /**
     * 过滤所有没有权限的请求
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("进入权限校验");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        System.out.println(cookie.getValue());
        //判断是否登录
        if (cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                        get(String.format(RedisConstant.USER_TOKEN_TEMPLATE, cookie.getValue())))) {
            //已登录
            System.out.println("用户已登录");
        } else {
            //表示不通过
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
        }


        return null;
    }

    /**
     * 判断请求是否属于需要过滤的类型
     *
     * @param request
     * @return
     */
    private boolean filterFlag(HttpServletRequest request) {
        //当请求是关于订单的请求的时候
        if (request.getRequestURI().startsWith("/myOrder/order"))
            return true;
        //当请求是购物车的请求
        if (request.getRequestURI().startsWith("/myCommodity/shoppingCart"))
            return true;
        //当请求是地址请求
        if (request.getRequestURI().startsWith("/myUser/address"))
            return true;
        //特定类型的请求
        if (!request.getRequestURI().startsWith("/myUser/user/login") &&
                !request.getRequestURI().startsWith("/myUser/user/register") &&
                !request.getMethod().equals("GET"))
            return true;
        return false;
    }
}
