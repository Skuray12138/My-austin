package com.ray.austin.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nacos.shaded.com.google.common.base.Throwables;
import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.mq.SendMqService;
import com.ray.austin.pipeline.BusinessProcess;
import com.ray.austin.pipeline.ProcessContext;
import com.ray.austin.service.api.enmus.BusinessCode;
import com.ray.austin.service.api.impl.domain.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:22
 * @description 4.发送消息到MQ
 */
@Slf4j
@Service
public class SendMqAction implements BusinessProcess<SendTaskModel> {

    @Autowired
    private SendMqService sendMqService;

    @Value("${austin.business.topic.name}")
    private String sendMessageTopic;

    @Value("${austin.business.recall.topic.name}")
    private String austinRecall;

    @Value("${austin.business.tagId.value}")
    private String tagId;

    @Value("${austin.mq.pipeline}")
    private String mqPipeline;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        SendTaskModel sendTaskModel = context.getProcessModel();

        try {
            // 普通发送
            if (BusinessCode.COMMON_SEND.getCode().equals(context.getCode())){
                // 序列化时写入类型信息
                String message = JSON.toJSONString(sendTaskModel.getTaskInfo(),
                        new SerializerFeature[]{SerializerFeature.WriteClassName});
                // 发送数据到消息队列
                sendMqService.send(sendMessageTopic, message, tagId);
            } else if (BusinessCode.RECALL.getCode().equals(context.getCode())) {
                // 撤回消息
                String message = JSON.toJSONString(sendTaskModel.getMessageTemplate(),
                        new SerializerFeature[]{SerializerFeature.WriteClassName});
                sendMqService.send(austinRecall, message,tagId);
            }
        } catch (Exception e) {
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.SERVICE_ERROR));
            // 日志打印错误信息
            log.error("send {} fail! e:{}, params:{}", mqPipeline, Throwables.getStackTraceAsString(e),
            JSON.toJSONString(CollUtil.getFirst(sendTaskModel.getTaskInfo().listIterator())));

        }


    }
}
