package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.vo.system.ValidateCodeVo;

/**
 * @author ljl
 * @create 2023-10-24-23:06
 */
public interface ValidateCodeService {

    // 获取验证码图片
    public abstract ValidateCodeVo generateValidateCode();

}
