package com.atguigu.spzx.common.log.annotation;

import com.atguigu.spzx.common.log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ljl
 * @create 2023-10-31-23:40
 */

//target表示注解用在什么地方
//Retention表示注解的生命周期
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {		// 自定义操作日志记录注解

    public String title() ;		// 模块名称
    public OperatorType operatorType() default OperatorType.MANAGE;	// 操作人类别（手机端用户、后台用户、其他）
    public int businessType() ;     // 业务类型（0其它 1新增 2修改 3删除）
    public boolean isSaveRequestData() default true;   // 是否保存请求的参数
    public boolean isSaveResponseData() default true;  // 是否保存响应的参数

}