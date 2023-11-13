package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public abstract UserAddress getUserAddress(@PathVariable("id") Long id) ;

    /**
     * @Description: 远程调用获取浏览量最多的商品
     * @param
     */
    @GetMapping("api/user/userInfo/auth/BrowseHistory")
    public UserBrowseHistory getByBrowseHistory();

}