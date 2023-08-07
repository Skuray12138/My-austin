package com.ray.austin.utils;

import com.dtp.core.DtpRegistry;
import com.dtp.core.thread.DtpExecutor;
import com.ray.austin.config.ThreadPoolExecutorShutdownDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Skuray
 * @Date 2023/8/7 13:39
 */
@Component
public class ThreadPoolUtils {
    @Autowired
    private ThreadPoolExecutorShutdownDefinition shutdownDefinition;

    private static final String SOURCE_NAME = "austin";

    /**
     * 1.将当前线程池 加入到 动态线程池中 <p>
     * 2.注册 线程池，被spring管理，优雅关闭
     * @param dtpExecutor
     */
    public void register(DtpExecutor dtpExecutor){
        DtpRegistry.register(dtpExecutor, SOURCE_NAME);

        shutdownDefinition.registryExecutor(dtpExecutor);
    }
}
