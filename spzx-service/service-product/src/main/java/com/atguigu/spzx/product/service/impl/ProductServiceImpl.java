package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 接口实现类
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;
    @Override
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }

    // 接口实现类
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    // 接口实现类


    @Override
    public ProductItemVo item(String skuId) {
        //1、创建vo对象，封装最终的数据
        ProductItemVo productItemVo = new ProductItemVo();

        //2、根据skuid获取当前sku信息
        ProductSku productSku = productSkuMapper.getById(Long.valueOf(skuId));

        //3、根据第二步获取sku，从sku获取productId，获取当前商品信息
        Product product = productMapper.getById(productSku.getProductId());

        //4、获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productSku.getProductId());

        //5、封装map集合
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        //建立sku规格与skuId对应关系
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });

        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));

        //轮播图list集合
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        //商品规格信息
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));

        return productItemVo;
    }

    //业务接口实现
    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.getById(skuId);
    }

    //业务接口实现
    @Transactional
    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                productSkuMapper.updateSale(skuSaleDto.getSkuId(), skuSaleDto.getNum());
            }
        }
        return true;
    }
}