package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.UserAddressService;
import com.atguigu.spzx.user.service.UserRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value="/api/user")
public class UserAddressController {
   
   @Autowired
   private UserAddressService userAddressService;

   @Autowired
   private UserRegionService userRegionService;

   @Operation(summary = "获取用户地址列表")
   @GetMapping("userAddress/auth/findUserAddressList")
   public Result<List<UserAddress>> findUserAddressList() {
      List<UserAddress> list = userAddressService.findUserAddressList();
      return Result.build(list , ResultCodeEnum.SUCCESS) ;
   }

   @Operation(summary = "收货地址省市区显示")
   @GetMapping ("region/findByParentCode/{code}")
   public Result register(@PathVariable("code") Integer code) {
      List<Region> regionList = userRegionService.selectByParentCode(code);
      return Result.build(regionList , ResultCodeEnum.SUCCESS) ;
   }
   @Operation(summary = "用户收货地址修改")
   @PutMapping("userAddress/auth/updateById")
   public Result updateById(@RequestBody UserAddress userAddress) {
      userAddressService.updateByid(userAddress);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

   @Operation(summary = "用户收货地址新增")
   @PostMapping("userAddress/auth/save")
   public Result save(@RequestBody UserAddress userAddress) {
      userAddressService.save(userAddress);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

   @Operation(summary = "用户删除收货地址")
   @DeleteMapping("userAddress/auth/removeById/{id}")
   public Result removeById(@PathVariable Long id) {
      userAddressService.removeById(id);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

   @Operation(summary = "获取地址信息")
   @GetMapping("userAddress/getUserAddress/{id}")
   public UserAddress getUserAddress(@PathVariable Long id) {
      return userAddressService.getById(id);
   }
}