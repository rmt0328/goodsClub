package com.atguigu.spzx.common.es;

import com.sun.tools.javac.util.List;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuEsModel {

    private Long skuId;
    private Long productId;
    private String skuName;
    private BigDecimal salePrice;
    private String thumbImg;
    private Integer saleNum;
    private Integer stockNum;
    private Long brandId;					// 品牌ID
    private Long category1Id;				// 一级分类id
    private Long category2Id;				// 二级分类id
    private Long category3Id;				// 三级分类id


}
