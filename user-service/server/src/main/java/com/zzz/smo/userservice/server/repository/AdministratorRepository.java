package com.zzz.smo.userservice.server.repository;

import com.zzz.smo.userservice.server.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Sean
 * @Date: 2019/1/13 16:46
 */
public interface AdministratorRepository extends JpaRepository<Administrator,String> {
    Administrator findByEmail(String email);
    Administrator findByPhone(String phone);
}
