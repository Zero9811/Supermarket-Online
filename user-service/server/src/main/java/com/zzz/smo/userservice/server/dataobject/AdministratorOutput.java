package com.zzz.smo.userservice.server.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/16 1:26
 */
@Data
public class AdministratorOutput {
    private String username;
    private String nickname;
    private String email;
    private String phone;
}
