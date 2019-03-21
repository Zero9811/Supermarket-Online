package com.zzz.smo.commodityservice.server.controller;


import com.zzz.smo.commodityservice.server.VO.CategoryVO;
import com.zzz.smo.commodityservice.server.entity.Category;
import com.zzz.smo.commodityservice.server.service.CategoryService;
import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 21:09
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/{id}")
    public ResultVO specialCategory(@PathVariable int id){
        Category category = categoryService.findById(id).get();
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        ResultVO resultVO = ResultVOUtil.success(categoryVO);
        return resultVO;
    }

    @GetMapping("/categories")
    public ResultVO allCategories(){
        List<Category> categoryList = categoryService.findAll();
        ResultVO resultVO = ResultVOUtil.success(categoryList);
        return resultVO;
    }

    @PostMapping
    public ResultVO creatCategory(String categoryName){
        Category category = new Category();
        category.setCategoryName(categoryName);
        categoryService.save(category);
        return ResultVOUtil.success("新类别创建成功");
    }

    @PutMapping
    public ResultVO updateCategory(Category category){
        categoryService.save(category);
        return ResultVOUtil.success("修改类别信息成功");
    }

    @DeleteMapping("/{id}")
    public ResultVO deleteCategory(@PathVariable int id){
        categoryService.delete(id);
        return ResultVOUtil.success("删除类别成功");
    }
}
