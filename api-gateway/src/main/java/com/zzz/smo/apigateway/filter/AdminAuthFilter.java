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

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @Author: Sean
 * @Date: 2019/1/13 0:50
 */
@Component
public class AdminAuthFilter extends ZuulFilter {

    //不用过滤的接口列表
    private Map<String,String> stopMap = new HashMap<>();

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
        if ("/myCommodity/commodity/commodity/create".equals(request.getRequestURI()))
            return true;
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
            Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
            //判断是否登录
            if (cookie != null &&
                    !StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                            get(String.format(RedisConstant.ADMIN_TOKEN_TEMPLATE,cookie.getValue())))){
                //已登录

            }
            else {
                //表示不通过
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(401);
            }


        return null;
    }
}
