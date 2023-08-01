package com.ray.austin.service.api.impl.domain;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.pipeline.ProcessModel;
import com.ray.austin.service.api.domain.MessageParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:05
 * 发送消息任务模型
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {
    /**
     * 消息模板id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfo;

    /**
     * 撤回任务的信息
     */
    private MessageTemplate messageTemplate;
}
