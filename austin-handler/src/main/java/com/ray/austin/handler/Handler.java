package com.ray.austin.handler;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;

/**
 * @Author Skuray
 * @Date 2023/8/18 10:28
 * 消息处理器
 */

public interface Handler {

    /**
     * 处理器
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     * @param messageTemplate
     */
    void recall(MessageTemplate messageTemplate);
}
