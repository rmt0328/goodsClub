package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {

    List<UserAddress> findByUserId(Long userId);


    void updateByid(UserAddress userAddress);

    void save(UserAddress userAddress);

    void removeById(Long id);

    UserAddress getById(Long id);
}