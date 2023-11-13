package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-26-16:07
 */
@Mapper
public interface SysRoleUserMapper {
    public abstract List<Long> findSysUserRoleByUserId(Long userId);

    public abstract void doAssign(@Param("userId") Long userId,
                                  @Param("roleId") Long roleId);		// 添加关联关系
    public abstract void deleteByUserId(Long userId);				// 根据用户的id删除数据
}
