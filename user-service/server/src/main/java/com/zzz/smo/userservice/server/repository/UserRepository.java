package com.zzz.smo.userservice.server.repository;


import com.zzz.smo.userservice.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Sean
 * @Date: 2018/12/27 0:58
 */
public interface UserRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
