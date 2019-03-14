package com.zzz.smo.userservice.service;

import com.zzz.smo.userservice.dto.AdministratorDTO;
import com.zzz.smo.userservice.entity.Administrator;

/**
 * @Author: Sean
 * @Date: 2019/1/13 16:47
 */
public interface AdministratorService {
    Administrator newAdministrator(Administrator administrator);
    Administrator findByUsername(String username);
    void addPermission(String username,String permission);
    void removePermission(String username,String permission);
    boolean checkPermission(String username,String permission);
    boolean isUsernameRegistered(String username);
    boolean isEmailRegistered(String email);
    boolean isPhoneRegistered(String phone);
    boolean isLeaderPhoneAllowed(String leaderPhone);
}
