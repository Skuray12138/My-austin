package com.ray.austin.config;

import com.dtp.common.em.QueueTypeEnum;
import com.dtp.common.em.RejectedTypeEnum;
import com.dtp.core.thread.DtpExecutor;
import com.dtp.core.thread.ThreadPoolBuilder;
import com.ray.austin.common.enums.constant.ThreadPoolConstant;

import java.util.concurrent.TimeUnit;

/**
 * @Author Skuray
 * @Date 2023/8/6 16:22
 * 配置线程池
 */

public class HandlerThreadPoolConfig {
    private static final String PRE_FIX = "austin.";

    /**
     * 业务：处理某个渠道某种类型消息 的线程池 <p>
     * 配置：不丢弃消息，核心线程数不会随着keepAliceTime减少(不会回收) <p>
     * 配置动态线程池, 并且被spring管理
     * @param groupId
     * @return
     */
    public static DtpExecutor getExecutor(String groupId){
        return ThreadPoolBuilder.newBuilder()
                .threadPoolName(PRE_FIX + groupId)
                .corePoolSize(ThreadPoolConstant.COMMON_CORE_POOL_SIZE)
                .maximumPoolSize(ThreadPoolConstant.COMMON_MAX_POOL_SIZE)
                .keepAliveTime(ThreadPoolConstant.COMMON_KEEP_LIVE_TIME)
                .timeUnit(TimeUnit.SECONDS)
                .rejectedExecutionHandler(RejectedTypeEnum.CALLER_RUNS_POLICY.getName()).allowCoreThreadTimeOut(false)
                .workQueue(QueueTypeEnum.VARIABLE_LINKED_BLOCKING_QUEUE.getName(),
                        ThreadPoolConstant.COMMON_QUEUE_SIZE,
                        false)
                .buildDynamic();
    }
}
