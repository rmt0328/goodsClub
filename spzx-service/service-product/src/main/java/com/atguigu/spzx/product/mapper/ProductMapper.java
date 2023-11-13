package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-11-02-20:40
 */
@Mapper
public interface ProductMapper {
    Product getById(Long productId);
}
