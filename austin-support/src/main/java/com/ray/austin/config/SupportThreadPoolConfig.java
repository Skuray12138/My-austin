package com.ray.austin.config;

import cn.hutool.core.thread.ExecutorBuilder;
import com.ray.austin.common.enums.constant.ThreadPoolConstant;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author Skuray
 * @Date 2023/8/24 20:55
 * 线程池配置类
 */

public class SupportThreadPoolConfig {

    /**
     * 业务：实现pending队列的单线程池  <p>
     * 配置：核心线程可以被回收,当线程池无法被引用且无核心线程数,应当被回收  <p>
     * 动态线程池 被 spring管理 false
     * @return
     */
    public static ExecutorService getPendingSingleThreadPool() {
        return ExecutorBuilder.create()
                .setCorePoolSize(ThreadPoolConstant.SINGLE_CORE_POOL_SIZE)
                .setMaxPoolSize(ThreadPoolConstant.SINGLE_MAX_POOL_SIZE)
                .setWorkQueue(ThreadPoolConstant.BIG_BLOCKING_QUEUE)
                .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .setAllowCoreThreadTimeOut(true)
                .setKeepAliveTime(ThreadPoolConstant.SMALL_KEEP_LIVE_TIME, TimeUnit.SECONDS)
                .build();
    }
}
