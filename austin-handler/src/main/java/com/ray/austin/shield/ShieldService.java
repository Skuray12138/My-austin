package com.ray.austin.shield;

import com.ray.austin.common.domain.TaskInfo;

/**
 * @Author Skuray
 * @Date 2023/8/26 0:32
 * 屏蔽服务
 */

public interface ShieldService {

    /**
     * 屏蔽消息
     * @param taskInfo
     */
    void shield(TaskInfo taskInfo);
}
