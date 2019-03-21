package com.zzz.smo.commodityservice.server.repository;

import com.zzz.smo.commodityservice.server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Sean
 * @Date: 2019/1/1 21:06
 */
public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
