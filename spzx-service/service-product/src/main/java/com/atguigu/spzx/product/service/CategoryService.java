package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-01-23:41
 */
// 业务接口
public interface CategoryService {

    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}