package com.ray.austin.service.api.impl.service;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.pipeline.ProcessContext;
import com.ray.austin.pipeline.ProcessController;
import com.ray.austin.pipeline.ProcessModel;
import com.ray.austin.service.api.domain.BatchSendRequest;
import com.ray.austin.service.api.domain.SendRequest;
import com.ray.austin.service.api.domain.SendResponse;
import com.ray.austin.service.api.impl.domain.SendTaskModel;
import com.ray.austin.service.api.service.SendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @Author Skuray
 * @Date 2023/8/3 11:01
 * 发送接口实现类
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    /**
     * 单文案发送
     * @param sendRequest
     * @return
     * 构建SendTaskModel和上下文context,并将上下文传入流程控制器的process方法中去执行
     */
    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();
        ProcessContext context = ProcessContext.builder()
                .code(sendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success())
                .build();
        // 从此处进入责任链，循环执行process对应的四个前置工作
        ProcessContext process = processController.process(context);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());
    }

    /**
     * 批量发送
     * @param batchSendRequest
     * @return
     */
    @Override
    @OperationLog(bizType = "SendService#batchSend", bizId = "#batchSendRequest.messageTemplateId", msg = "#batchSendRequest")
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();
        ProcessContext<ProcessModel> content = ProcessContext.builder()
                .code(batchSendRequest.getCode())
                .processModel(sendTaskModel)
                .needBreak(false)
                .response(BasicResultVO.success())
                .build();

        ProcessContext process = processController.process(content);

        return new SendResponse(process.getResponse().getStatus(), process.getResponse().getMsg());

    }
}
