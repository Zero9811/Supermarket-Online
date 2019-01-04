package com.zzz.smo.commodityservice.repository;

import com.zzz.smo.commodityservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author: Sean
 * @Date: 2019/1/1 21:06
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
