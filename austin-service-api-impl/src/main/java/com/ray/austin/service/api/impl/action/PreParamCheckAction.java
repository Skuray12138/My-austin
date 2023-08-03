package com.ray.austin.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.pipeline.BusinessProcess;
import com.ray.austin.pipeline.ProcessContext;
import com.ray.austin.service.api.domain.MessageParam;
import com.ray.austin.service.api.impl.domain.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:20
 * @description 1.前置参数校验
 */
@Slf4j
@Service
public class PreParamCheckAction implements BusinessProcess<SendTaskModel> {

    /**
     * 最大人数
     */
    public static final Integer BATCH_RECEIVER_SIZE = 100;

    @Override
    public void process(ProcessContext<SendTaskModel> context) {
        // 拿到责任链上下文数据
        SendTaskModel sendTaskModel = context.getProcessModel();
        // 拿到消息模板id和请求相关参数MessageParam(接收者、占位符、扩展参数)
        Long messageTemplateId = sendTaskModel.getMessageTemplateId();
        List<MessageParam> messageParamList = sendTaskModel.getMessageParamList();
        // 1.没有传入消息模板id 或 messageParam
        if (messageTemplateId == null || CollUtil.isEmpty(messageParamList)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        // 2.过滤receiver == null 的messageParam. 将receiver不为空的放到result中
        List<MessageParam> resultMessageParamList = messageParamList.stream().filter(messageParam ->
                !StrUtil.isBlank(messageParam.getReceiver())).collect(Collectors.toList());
        // 若result为空，说明都被过滤了
        if (CollUtil.isEmpty(resultMessageParamList)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        // result不为空，则将过滤后的重新设置给发送任务模型
        sendTaskModel.setMessageParamList(resultMessageParamList);
        // 3.过滤receiver大于100的请求
        // amyMatch, 匹配一个元素，只要满足一个即可. split切割字符串，返回的是字符串数组String[]
        if (messageParamList.stream().anyMatch(messageParam ->
                messageParam.getReceiver().split(StrUtil.COMMA).length > BATCH_RECEIVER_SIZE)){
                context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TOO_MANY_RECEIVER));
                return;
        }
    }
}
