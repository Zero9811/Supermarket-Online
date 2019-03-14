package com.zzz.smo.userservice.server.controller;


import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import com.zzz.smo.userservice.server.constant.CookieConstant;
import com.zzz.smo.userservice.server.constant.RedisConstant;
import com.zzz.smo.userservice.server.dataobject.AdministratorOutput;
import com.zzz.smo.userservice.server.entity.Administrator;
import com.zzz.smo.userservice.server.service.AdministratorService;
import com.zzz.smo.userservice.server.util.CookieUtil;
import com.zzz.smo.userservice.server.util.HashUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Sean
 * @Date: 2019/1/13 17:01
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/authentication")
    public ResultVO checkPermission(String username, String permission){
        if (administratorService.checkPermission(username,permission)){
            return ResultVOUtil.success("权限校验通过");
        }
        return ResultVOUtil.fail("权限校验不通过");
    }

    //TODO 短信验证
    //TODO 传入参数再加上短信验证码，创建管理员之后再清除Redis
    //待改进，传入json
    //责任人短信验证
    @PostMapping("/register")
    public ResultVO register(String username,String password,String email,String phone,String leaderPhone){
        if (administratorService.findByUsername(username) != null)
            return ResultVOUtil.fail("用户名已被注册");
        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setPassword(HashUtil.encryPassword(password));
        administrator.setEmail(email);
        administrator.setPhone(phone);
        administrator.setLeaderPhone(leaderPhone);
        administratorService.newAdministrator(administrator);
        //清除Redis
        return null;
    }

    /**
     * 1、查看用户名、邮箱、手机号是否被注册
     * 2、查看责任人手机号是否有效
     * 3、查看责任人是否具有责任权限
     * 4、设置验证码内容发送手机短信
     * 5、将验证码存入Redis并设置过期时间
     * @param username
     * @param email
     * @param phone
     * @param leaderPhone
     * @return
     */
    @GetMapping("/registerSMS")
    public ResultVO registerShortMessageService(String username,String email,String phone,String leaderPhone){
        if (administratorService.isUsernameRegistered(username))
            return ResultVOUtil.fail("用户名已被注册");
        if (administratorService.isEmailRegistered(email))
            return ResultVOUtil.fail("邮箱已被注册");
        if (administratorService.isPhoneRegistered(phone))
            return ResultVOUtil.fail("手机号已被注册");

        if (!administratorService.isPhoneRegistered(leaderPhone))
            return ResultVOUtil.fail("责任人不存在");
        if (administratorService.isLeaderPhoneAllowed(leaderPhone)){
            //TODO 添加验证内容，发送短信，存入Redis
        }
        return null;
    }

    /**
     * 1.获取cookie
     * 2.清除redis
     * 3.清除cookie
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request, HttpServletResponse response){

        //获取cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);

        //清除Redis
        stringRedisTemplate.delete(String.format(RedisConstant.ADMIN_TOKEN,cookie.getValue()));

        //清除cookie
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setValue(null);
        response.addCookie(cookie);

        return ResultVOUtil.success("登出成功");
    }

    /**
     * 用户登录接口
     * 1.数据库校验
     * 2.生成token
     * 3.redis存放
     * 4.cookie添加token
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(String username, String password,
                          HttpServletRequest request,
                          HttpServletResponse response){
        //判断是否登录
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                        get(String.format(RedisConstant.ADMIN_TOKEN,cookie.getValue())))){
            return ResultVOUtil.success("管理员已登录");
        }

        //当用户未登录时
        Administrator administrator = administratorService.findByUsername(username);
        if(administrator != null){
            //加密比较
            if(HashUtil.encryPassword(password).equals(administrator.getPassword())){
                String token = UUID.randomUUID().toString();
                //存入Redis.包括key、value、最大过期时间，过期单位
                stringRedisTemplate.opsForValue().set(String.format(RedisConstant.ADMIN_TOKEN,token),username,RedisConstant.EXPIRE, TimeUnit.SECONDS);
                //cookie设置token
                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.MAX_AGE);
                //构造返回
                AdministratorOutput administratorOutput = new AdministratorOutput();
                BeanUtils.copyProperties(administrator,administratorOutput);
                return ResultVOUtil.success(administratorOutput);
            }
        }
        return ResultVOUtil.fail("登陆失败");
    }
}
