package com.zzz.smo.commodityservice.repository;

import com.zzz.smo.commodityservice.entity.Category;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAllTest(){
        List<Category> list = categoryRepository.findAll();
        Assert.assertTrue(list.size()>0);
    }
}
