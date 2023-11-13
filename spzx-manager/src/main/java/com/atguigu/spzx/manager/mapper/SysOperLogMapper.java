package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ljl
 * @create 2023-11-01-0:22
 */
// com.atguigu.spzx.manager.mapper;
@Mapper
public interface SysOperLogMapper {
    public abstract void insert(SysOperLog sysOperLog);
}
