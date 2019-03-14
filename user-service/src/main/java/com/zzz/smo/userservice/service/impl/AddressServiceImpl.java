package com.zzz.smo.userservice.service.impl;

import com.zzz.smo.userservice.entity.Address;
import com.zzz.smo.userservice.repository.AddressRepository;
import com.zzz.smo.userservice.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/24 20:27
 */
@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public List<Address> findByUsername(String username) {
        return addressRepository.findByUsername(username);
    }

    @Override
    public Address findById(long id) {
        return addressRepository.getOne(id);
    }

    @Override
    public void deleteById(long id) {
        addressRepository.deleteById(id);
    }
}
