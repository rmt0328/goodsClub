// com.atguigu.spzx.common.log.aspect;
package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.AsyncManager;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TimerTask;

@Aspect
@Component
@Slf4j
public class LogAspect {            // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService asyncOperLogService ;

    //环绕通知
    //在方法上有log注解就说明会执行环绕通知
    //对哪些方法，进行哪些增强
    @Around(value = "@annotation(sysLog)")
    //通知
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {

        // 构建前置参数
        //业务方法调用之前，封装数据
        SysOperLog sysOperLog = new SysOperLog() ;

        LogUtil.beforeHandleLog(sysLog , joinPoint , sysOperLog) ;

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            // 执行业务方法
            //业务方法调用之后，封装数据
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 0 , null) ;
            // 构建响应结果参数
        } catch (Throwable e) {                                 // 代码执行进入到catch中，
            // 业务方法执行产生异常
            e.printStackTrace();
            // 业务方法产生异常之后打印异常信息
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;
            throw new RuntimeException();
        }
        

        // 保存日志数据
        //将任务交给定时任务线程池来执行插入，避免了系统在插入操作日志时出现了异常从而到时业务功能无法正常返回
        //通过AysncManaer来执行一个异步任务保存系统操作日志，避免阻塞当前线程的执行，提高系统性能和响应速度
        new AsyncManager().execute(new TimerTask(){
            @Override
            public void run(){
               asyncOperLogService.saveSysOperLog(sysOperLog);
            }
        });


        // 返回执行结果
        return proceed ;
    }
}