package com.zzz.smo.userservice.repository;

import com.zzz.smo.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2018/12/27 0:58
 */
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
