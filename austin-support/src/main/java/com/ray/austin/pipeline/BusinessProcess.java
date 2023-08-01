package com.ray.austin.pipeline;

/**
 * @Author Skuray
 * @Date 2023/8/1 13:59
 * 业务执行器。业务抽象接口
 * @Method void process(ProcessContext context)
 */

public interface BusinessProcess<T extends ProcessModel>{

    /**
     * 真正处理逻辑
     * @param context
     */
    void process(ProcessContext<T> context);
}
