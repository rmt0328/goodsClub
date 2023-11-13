package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserBrowseHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ljl
 * @create 2023-11-12-13:48
 */
@Mapper
public interface UserBrowseHistoryMapper {
    @Update("UPDATE user_browse_history SET is_deleted = 1 WHERE user_id = #{id} AND sku_id = #{skuId}")
    void updatecancelCollect(Long skuId, Long id);

    @Insert("INSERT INTO user_browse_history (user_id, sku_id) " +
            "VALUES (#{id}, #{skuId})")
    void savecollect(Long skuId, Long id);

    /**
     * @Description: 查询是否有该商品的收藏信息
     * @param skuId
     * @param id
     */
    @Select("select * from user_browse_history where sku_id = #{skuId} and user_id = #{id}")
    UserBrowseHistory selectcollect(Long skuId, Long id);


    /**
     * @Description: 保存浏览信息时查询该商品的收藏信息
     * @param skuId
     * @param id
     */
    @Select("select * from user_browse_history where sku_id = #{skuId} and user_id = #{id} and is_deleted = 0")
    UserBrowseHistory selectusercollect(Long skuId, Long id);


    @Update("UPDATE user_browse_history SET is_deleted = 0 WHERE user_id = #{id} AND sku_id = #{skuId}")
    void updatecollect(Long skuId, Long id);

    @Select("select sku_id from " +
            "user_collect " +
            "group by sku_id " +
            "order by COUNT(*) " +
            "desc limit 1")
    UserBrowseHistory getMostFrequentSkuId();
}
