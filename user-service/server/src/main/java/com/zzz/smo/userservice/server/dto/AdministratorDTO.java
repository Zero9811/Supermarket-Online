package com.zzz.smo.userservice.server.dto;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/13 16:53
 */
@Data
public class AdministratorDTO {
    private String username;
    private String nickname;
    private String email;
    private String phone;
    //权限列表
    private String permission;
}
