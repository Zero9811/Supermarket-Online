package com.zzz.smo.userservice.controller;

import com.zzz.smo.userservice.vo.ResultVO;
import com.zzz.smo.userservice.entity.Address;
import com.zzz.smo.userservice.service.AddressService;
import com.zzz.smo.userservice.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Sean
 * @Date: 2019/1/24 20:29
 */
@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResultVO addNewAddress(String username,String name,String phone,String addressContent){
        //TODO id生成策略
        Address address = new Address(username,name,phone,addressContent);
        //保存到数据库
        addressService.save(address);
        return ResultVOUtil.success("新建信息成功");
    }

    @PutMapping("/address")
    public ResultVO modifyAddress(long id,String username,String name,String phone,String addressContent){
        Address address = new Address(username,name,phone,addressContent);
        address.setId(id);
        addressService.save(address);
        return ResultVOUtil.success("信息修改成功");
    }

    @GetMapping("/user")
    public ResultVO findByUsername(String username){

        return ResultVOUtil.success(addressService.findByUsername(username));
    }

    @DeleteMapping("/address")
    public ResultVO deleteAddress(long id){
        addressService.deleteById(id);
        return ResultVOUtil.success("删除成功");
    }

    @GetMapping("/one")
    public ResultVO findById(long id){
        Address address = addressService.findById(id);
        if (address == null){
            return ResultVOUtil.fail("信息不存在");
        }
        else
            return ResultVOUtil.success(address);
    }
}
