package com.zzz.smo.userservice.server.controller;


import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import com.zzz.smo.userservice.server.entity.Address;
import com.zzz.smo.userservice.server.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Sean
 * @Date: 2019/1/24 20:29
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResultVO addNewAddress(String username, String name, String phone, String addressContent){
        //TODO id生成策略
        Address address = new Address(username,name,phone,addressContent);
        //保存到数据库
        addressService.save(address);
        return ResultVOUtil.success("新建信息成功");
    }

    @PutMapping
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

    @DeleteMapping
    public ResultVO deleteAddress(long id){
        addressService.deleteById(id);
        return ResultVOUtil.success("删除成功");
    }

    @GetMapping("/{id}")
    public ResultVO findById(@PathVariable(value = "id") long id){
        Address address = addressService.findById(id);
        if (address == null){
            return ResultVOUtil.fail("信息不存在");
        }
        else
            return ResultVOUtil.success(address);
    }
}
