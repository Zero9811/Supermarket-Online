package com.zzz.smo.commodityservice.service;

import com.zzz.smo.commodityservice.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:18
 */
public interface CategoryService {
    /**
     * 查询所有类目
     */
    List<Category> findAll();

    Optional<Category> findById(int id);
}
