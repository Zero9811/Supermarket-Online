package com.zzz.smo.commodityservice.service.impl;

import com.zzz.smo.commodityservice.entity.Category;
import com.zzz.smo.commodityservice.repository.CategoryRepository;
import com.zzz.smo.commodityservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }
}
