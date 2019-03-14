package com.zzz.smo.userservice.server.service;



import com.zzz.smo.userservice.server.entity.Address;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/24 20:25
 */
public interface AddressService {
    void save(Address address);
    List<Address> findByUsername(String username);
    Address findById(long id);
    void deleteById(long id);
}
