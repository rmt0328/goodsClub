package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

/**
 * @author ljl
 * @create 2023-10-22-16:36
 */
public interface SysUserService {

    /**
     * 根据用户名查询用户数据
     * @return
     */
    public abstract LoginVo login(LoginDto loginDto) ;

    /**
     * 根据token查询用户数据
     * @return
     */
    SysUser getUserInfo(String token);

    /**
     * 退出功能
     * @return
     */
    void logout(String token);

    /**
     * @Description: 用户条件分页查询接口
     * @param sysUserDto
     * @param pageNum
     * @param pageSize
     */
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    /**
     * @Description: 用户添加接口
     * @param sysUser
     */
    void saveSysUser(SysUser sysUser);

    /**
     * @Description: 用户修改接口
     * @param sysUser
     */
    void updateSysUser(SysUser sysUser);

    /**
     * @Description: 用户删除接口
     * @param userId
     */
    void deleteById(Long userId);

    /**
     * @Description: 用户分配角色保存分配数据
     * @param assginRoleDto
     */
    void doAssign(AssginRoleDto assginRoleDto);
}
