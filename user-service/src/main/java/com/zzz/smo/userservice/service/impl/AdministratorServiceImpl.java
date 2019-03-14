package com.zzz.smo.userservice.service.impl;

import com.zzz.smo.userservice.entity.Administrator;
import com.zzz.smo.userservice.enums.PermissionEnum;
import com.zzz.smo.userservice.repository.AdministratorRepository;
import com.zzz.smo.userservice.service.AdministratorService;
import com.zzz.smo.userservice.util.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/13 21:01
 */
@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public Administrator newAdministrator(Administrator administrator) {
        //设置基本权限
        administrator.setPermission(PermissionEnum.BASE.getCode());
        administratorRepository.save(administrator);
        return administrator;
    }

    @Override
    public Administrator findByUsername(String username) {
        return administratorRepository.getOne(username);
    }

    @Override
    public void addPermission(String username, String permission) {
        //获取权限
        Administrator administrator = administratorRepository.getOne(username);
        List<String> permissionList = PermissionUtil.string2List(administrator.getPermission());
        if (permissionList.contains(permission))
            return;
        else {
            //添加权限
            permissionList.add(permission);
            administrator.setPermission(PermissionUtil.list2String(permissionList));
            administratorRepository.save(administrator);
        }

    }

    @Override
    public void removePermission(String username, String permission) {
        //获取权限列表
        Administrator administrator = administratorRepository.getOne(username);
        List<String> permissionList = PermissionUtil.string2List(administrator.getPermission());
        //删除权限
        permissionList.remove(permission);
        //保存
        administrator.setPermission(PermissionUtil.list2String(permissionList));
        administratorRepository.save(administrator);
    }

    @Override
    public boolean checkPermission(String username, String permission) {
        Administrator administrator = administratorRepository.getOne(username);
        List<String> permissionList = PermissionUtil.string2List(administrator.getPermission());

        return permissionList.contains(permission);
    }

    @Override
    public boolean isUsernameRegistered(String username) {

        return  administratorRepository.getOne(username) != null;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        return administratorRepository.findByEmail(email) != null;
    }

    @Override
    public boolean isPhoneRegistered(String phone) {
        return administratorRepository.findByPhone(phone) != null;
    }

    @Override
    public boolean isLeaderPhoneAllowed(String leaderPhone) {
        Administrator administrator = administratorRepository.findByPhone(leaderPhone);
        if (administrator != null){
            List<String> permissionList = PermissionUtil.string2List(administrator.getPermission());
            return permissionList.contains(PermissionEnum.ADMIN);
        }
        return false;
    }
}
