package com.zzz.smo.commodityservice.server.service.impl;

import com.zzz.smo.commodityservice.server.entity.Category;
import com.zzz.smo.commodityservice.server.repository.CategoryRepository;
import com.zzz.smo.commodityservice.server.service.CategoryService;
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

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }
}
