package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

// 业务接口
public interface SysRoleService {
    //角色列表的方法
    public abstract PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current , Integer limit);

    //角色添加的方法
    void saveSysRole(SysRole sysRole);

    //角色修改
    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    //查询所有角色
    Map<String, Object> findAllRoles(Long userId);
}