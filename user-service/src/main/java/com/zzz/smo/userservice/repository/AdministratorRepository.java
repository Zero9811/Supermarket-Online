package com.zzz.smo.userservice.repository;

import com.zzz.smo.userservice.dto.AdministratorDTO;
import com.zzz.smo.userservice.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Sean
 * @Date: 2019/1/13 16:46
 */
public interface AdministratorRepository extends JpaRepository<Administrator,String> {
    Administrator findByEmail(String email);
    Administrator findByPhone(String phone);
}
