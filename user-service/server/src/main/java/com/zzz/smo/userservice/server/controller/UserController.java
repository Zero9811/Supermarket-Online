package com.zzz.smo.userservice.server.controller;

import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import com.zzz.smo.userservice.server.constant.CookieConstant;
import com.zzz.smo.userservice.server.constant.RedisConstant;
import com.zzz.smo.userservice.server.converter.User2UserInfoOutput;
import com.zzz.smo.userservice.server.dataobject.UserInfoOutput;
import com.zzz.smo.userservice.server.entity.User;
import com.zzz.smo.userservice.server.form.UserForm;
import com.zzz.smo.userservice.server.service.UserService;
import com.zzz.smo.userservice.server.util.CookieUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
public class
UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private User2UserInfoOutput user2UserInfoOutput;

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    @ApiOperation(value = "获取用户信息",notes = "根据url的用户名获取用户详细信息")
    @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String",paramType = "path")
    @GetMapping("/{username}")
    public ResultVO getA(@PathVariable(value = "username") String username){
        User user = userService.findByUsername(username);
        UserInfoOutput userInfoOutput = user2UserInfoOutput.convert(user);
        return ResultVOUtil.success(userInfoOutput);
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
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().
                        get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success("用户已登录");
        }

        //当用户未登录时
        User user = userService.findByUsername(username);
        System.out.println(user.toString());
        if(user != null){
            //加密比较
            if(userService.encryPassword(password).equals(user.getPassword())){
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

    /**
     * 1.获取cookie
     * 2.清除redis
     * 3.清除cookie
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/logout")
    public ResultVO logout(HttpServletRequest request,HttpServletResponse response){

        //获取cookie
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);

        //清除Redis
        stringRedisTemplate.delete(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue()));

        //清除cookie
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setValue(null);
        response.addCookie(cookie);

        return ResultVOUtil.success("登出成功");
    }

    /**
     * 1.查询是否已注册
     * 2.写入数据库
     * @param username
     * @param password
     * @param email
     * @return
     */
    @ApiOperation(value = "注册、创建用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String"),
            @ApiImplicitParam(name = "email",value = "邮箱",required = true,dataType = "String")
    })
    @PostMapping("/register")
    public ResultVO register(String username,String password,String email){

        //判断是否已被注册
        if(userService.findByUsername(username) != null){
            return ResultVOUtil.fail("账号已被注册");
        }

        //保存到数据库
        User user = new User();
        user.setUsername(username);
        user.setPassword(userService.encryPassword(password));
        user.setEmail(email);
        userService.save(user);
        return ResultVOUtil.success("注册成功");
    }

    @ApiOperation(value = "更新用户信息")
    @ApiImplicitParam(required = true)
    @PutMapping
    public ResultVO changeInfo(UserForm userForm){
        User user = userService.findByUsername(userForm.getUsername());
        BeanUtils.copyProperties(userForm,user);
        userService.save(user);
        return ResultVOUtil.success("更改信息成功");
    }
}
