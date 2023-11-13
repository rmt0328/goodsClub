package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-29-17:17
 */

@Mapper
public interface ProductSkuMapper {
    public void save(ProductSku productSku);

    public List<ProductSku> selectByProductId(Long id);

    void updateById(ProductSku productSku);

    void deleteByProductId(Long id);
}
