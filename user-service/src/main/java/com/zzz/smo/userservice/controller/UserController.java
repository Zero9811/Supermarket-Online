package com.zzz.smo.userservice.controller;

import com.zzz.smo.userservice.VO.ResultVO;
import com.zzz.smo.userservice.constant.CookieConstant;
import com.zzz.smo.userservice.constant.RedisConstant;
import com.zzz.smo.userservice.entity.User;
import com.zzz.smo.userservice.entity.UserInfoOutput;
import com.zzz.smo.userservice.repository.UserRepository;
import com.zzz.smo.userservice.service.UserService;
import com.zzz.smo.userservice.util.CookieUtil;
import com.zzz.smo.userservice.util.ResultVOUtil;
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
 * @Date: 2018/12/27 15:25
 */
//TODO 接入微信登录,使用微信的openid
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //该删除
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/u")
    public String getA(){
        return userRepository.findByUsername("22").toString();
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
                        get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success("用户已登录");
        }

        //当用户未登录时
        User user = userService.findByUsername(username);
        if(user != null){
            if(password.equals(user.getPassword())){
                String token = UUID.randomUUID().toString();
                //存入Redis.包括key、value、最大过期时间，过期单位
                stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),username,RedisConstant.EXPIRE, TimeUnit.SECONDS);
                //cookie设置token
                CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.MAX_AGE);
                //构造返回
                UserInfoOutput userInfoOutput = new UserInfoOutput();
                BeanUtils.copyProperties(user,userInfoOutput);
                return ResultVOUtil.success(userInfoOutput);
            }
        }
        return ResultVOUtil.fail("登陆失败");
    }

    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request,HttpServletResponse response){
        //清除cookie
        Cookie cookie = CookieUtil.remove(request,CookieConstant.TOKEN);
        response.addCookie(cookie);
        return ResultVOUtil.success("登出成功");
    }

    @PostMapping("/register")
    public ResultVO register(String username,String password,String email){

        //判断是否已被注册
        if(userService.findByUsername(username) != null){
            return ResultVOUtil.fail("账号已被注册");
        }

        //保存到数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.save(user);
        return ResultVOUtil.success("注册成功");
    }
}
