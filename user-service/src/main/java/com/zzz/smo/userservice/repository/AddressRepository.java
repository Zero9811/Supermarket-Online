package com.zzz.smo.userservice.repository;

import com.zzz.smo.userservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/24 20:22
 */
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUsername(String username);
}
