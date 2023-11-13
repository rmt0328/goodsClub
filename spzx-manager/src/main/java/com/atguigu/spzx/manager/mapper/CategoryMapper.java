package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
@author ljl
@create 2023-10-27-1:28
*/

@Mapper
public interface CategoryMapper {

    public abstract List<Category> selectByParentId(Long parentId);
    public abstract int countByParentId(Long id);

    List<Category> selectAll();

    void batchInsert(List<CategoryExcelVo> categoryList);
}
