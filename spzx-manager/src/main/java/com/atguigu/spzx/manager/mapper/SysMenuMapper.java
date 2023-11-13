package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-26-18:40
 */
@Mapper
public interface SysMenuMapper {
    List<SysMenu> selectAll();

    void insert(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    public abstract int countByParentId(Long id);

    public abstract void deleteById(Long id);

    List<SysMenu> selectListByUserId(Long userId);

    // 查询是否存在父节点
    SysMenu selectParentMenu(Long parentId);
}
