package com.zzz.smo.commodityservice.server.service;

import com.zzz.smo.commodityservice.server.entity.Category;

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

    void save(Category category);

    void delete(int id);
}
