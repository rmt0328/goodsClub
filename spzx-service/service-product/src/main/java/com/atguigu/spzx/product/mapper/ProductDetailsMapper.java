package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-11-02-20:37
 */
@Mapper
public interface ProductDetailsMapper {
    ProductDetails getByProductId(Long productId);
}
