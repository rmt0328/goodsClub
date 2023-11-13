package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.product.mapper.BrandMapper;
import com.atguigu.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

// 接口实现类
@Service
public class BrandServiceImpl implements BrandService {
    
	@Autowired
	private BrandMapper brandMapper;

    //缓存带有该注解的方法的返回结果
    //value = "brandList"：表示这个缓存使用的名称是"brandList"，可以定义多个不同的缓存名称。
    //unless="#result.size() == 0"：这个表达式的意思是当方法返回值的size等于0时
    //即结果为空时，不进行缓存，即不缓存空的结果。
    //这是为了避免缓存的是空对象或空集合导致的不必要的开销和缓存污染问题。
	@Cacheable(value = "brandList", unless="#result.size() == 0")
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

}