package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

//业务接口
public interface UserAddressService {

    //获取用户地址列表
    List<UserAddress> findUserAddressList();

    //用户收货地址修改
    void updateByid(UserAddress userAddress);

    //用户收货地址新增
    void save(UserAddress userAddress);

    //用户收货地址刪除
    void removeById(Long id);

    //获取地址信息
    UserAddress getById(Long id);
}