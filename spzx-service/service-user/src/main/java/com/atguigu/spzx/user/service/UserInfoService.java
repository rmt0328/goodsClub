package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.entity.user.UserCollect;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

// 业务接口
public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    Object login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);

    /**
     * @Description: 新增浏览商品
     * @param id
     */
    void saveUserCollect(Long id);

    //浏览商品分页展示
    PageInfo<UserCollect> findUserBrowseHistoryPage(Integer page, Integer limit);

    //收藏商品分页展示
    PageInfo<UserBrowseHistory> findUserCollectPage(Integer page, Integer limit);

    //取消收藏
    void updatecancelCollect(Long skuId);

    //商品收藏
    void savecollect(Long skuId);

    //查看用户是否收藏
    Boolean findUserisCollect(Long skuId);

//    远程调用：获取浏览量最多的商品
    UserBrowseHistory getMostFrequentSkuId();
}