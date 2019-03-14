package com.zzz.smo.apigateway.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Sean
 * @Date: 2019/1/12 12:57
 */
public class CookieUtil {

    public static void set(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie c :
                    cookies) {
                if (name.equals(c.getName()))
                    return c;
            }
        }
        return null;
    }

    public static Cookie remove(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie c :
                    cookies) {
                if (name.equals(c.getName())) {
                    c.setValue(null);
                    c.setMaxAge(0);
                    c.setPath("/");
                    return c;
                }
            }
        }
        return null;
    }
}
