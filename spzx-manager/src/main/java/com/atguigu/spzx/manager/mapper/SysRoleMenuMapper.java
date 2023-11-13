package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-26-21:29
 */
@Mapper
public interface SysRoleMenuMapper {
    public abstract List<Long> findSysRoleMenuByRoleId(Long roleId);

    public abstract void deleteByRoleId(Long roleId);

    public abstract void doAssign(AssginMenuDto assginMenuDto);

    void updateSysRoleMenuIsHalf(Long menuId);
}
