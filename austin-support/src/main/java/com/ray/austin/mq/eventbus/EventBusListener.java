package com.ray.austin.mq.eventbus;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/2 10:47
 * 监听器
 */

public interface EventBusListener {
    /**
     * 消费消息
     * @param lists
     */
    void consume(List<TaskInfo> lists);

    /**
     * 撤回消息
     * @param messageTemplate
     */
    void recall(MessageTemplate messageTemplate);
}
