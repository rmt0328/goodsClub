package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import com.atguigu.spzx.model.entity.user.UserCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-11-21:23
 */

@Mapper
public interface UserCollectMapper {
    void saveUserCollect(UserCollect userCollect);

    List<UserCollect> findUserBrowseHistoryPage(Long id);

    List<UserBrowseHistory> findUserCollectPage(Long id);
}
