package com.atguigu.spzx.user.service;

/**
 * @author ljl
 * @create 2023-11-03-1:20
 */
public interface SmsService {
    void sendValidateCode(String phone);
}
