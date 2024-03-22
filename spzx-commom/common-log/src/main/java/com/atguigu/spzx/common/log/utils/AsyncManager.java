package com.atguigu.spzx.common.log.utils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 异步管理器：
 *     内部维护一个线程池
 */
public class AsyncManager {

    private ScheduledExecutorService scheduledThreadPool
            = new ScheduledThreadPoolExecutor(50);

    /**
     * 使用内部的一个线程，去执行具体的任务
     *
     * @param task
     */
    public void execute(TimerTask task) {
        scheduledThreadPool.execute(task); //延迟执行，延迟10毫秒

    }
}