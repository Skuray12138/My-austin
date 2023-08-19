package com.ray.austin.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ray.austin.common.domain.AnchorInfo;
import com.ray.austin.common.domain.LogParam;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.pending.Task;
import com.ray.austin.pending.TaskPendingHolder;
import com.ray.austin.receiver.service.ConsumeService;
import com.ray.austin.utils.GroupIdMappingUtils;
import com.ray.austin.utils.LogUtils;
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
    private LogUtils logUtils;

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

            logUtils.print(LogParam.builder() // 当前对象信息
                            .bizType(LOG_BIZ_TYPE) // 标志日志的业务
                            .object(taskInfo)
                            .build(),
                    AnchorInfo.builder() // 埋点信息
                            .businessId(taskInfo.getBusinessId())
                            .ids(taskInfo.getReceiver())
                            .state(AnchorState.RECEIVE.getCode()) // 从MQ 消息接收成功
                            .build()
                    );

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

        logUtils.print(LogParam.builder()
                .bizType(LOG_BIZ_RECALL_TYPE)
                .object(messageTemplate)
                .build());

    }
}
