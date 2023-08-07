package com.ray.austin.receiver.service;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/7 13:58
 * 消息消费服务
 */

public interface ConsumeService {
    /**
     * 从MQ拉去消息进行消费，发送消息
     * @param taskInfoList
     */
    void consume2Send(List<TaskInfo> taskInfoList);

    /**
     * 从MQ拉去消息进行消费，进行撤回
     * @param messageTemplate
     */
    void consume2Recall(MessageTemplate messageTemplate);
}
