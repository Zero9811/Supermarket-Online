package com.zzz.smo.commodityservice.controller;

import com.zzz.smo.commodityservice.VO.CategoryVO;
import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.entity.Category;
import com.zzz.smo.commodityservice.repository.CategoryRepository;
import com.zzz.smo.commodityservice.service.CategoryService;
import com.zzz.smo.commodityservice.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 21:09
 */
@RestController
@RequestMapping("/commodity")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @GetMapping("/category")
    public ResultVO specialCategory(int id){
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
}
