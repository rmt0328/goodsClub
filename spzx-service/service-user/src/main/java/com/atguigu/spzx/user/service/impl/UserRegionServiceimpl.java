package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.user.mapper.UserRegionMapper;
import com.atguigu.spzx.user.service.UserRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-04-20:55
 */

@Service
public class UserRegionServiceimpl implements UserRegionService {

    @Autowired
    private UserRegionMapper userRegionMapper;
    @Override
    public List<Region> selectByParentCode(Integer code) {
        List<Region> regions = userRegionMapper.selectByParentCode(code);
        return regions;
    }

}
