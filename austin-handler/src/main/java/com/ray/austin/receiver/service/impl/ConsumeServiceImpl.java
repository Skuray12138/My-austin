package com.ray.austin.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.pending.Task;
import com.ray.austin.pending.TaskPendingHolder;
import com.ray.austin.receiver.service.ConsumeService;
import com.ray.austin.utils.GroupIdMappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/7 14:08
 */

@Service
public class ConsumeServiceImpl implements ConsumeService {

    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;

    /**
     * 从 MQ 拉取消息进行消费,发送消息
     * @param taskInfoList
     */
    @Override
    public void consume2Send(List<TaskInfo> taskInfoList) {
        String topicGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoList.iterator()));
        for (TaskInfo taskInfo : taskInfoList) {
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            // 通过groupId获取对应线程池，并执行task任务
            taskPendingHolder.route(topicGroupId).execute(task);
        }
    }

    /**
     * 从 MQ 拉取消息进行消费,撤回消息
     * @param messageTemplate
     */
    @Override
    public void consume2Recall(MessageTemplate messageTemplate) {

    }
}
