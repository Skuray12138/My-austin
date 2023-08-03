package com.ray.austin.service.api.impl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import com.alibaba.fastjson.JSON;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.IdType;
import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.pipeline.BusinessProcess;
import com.ray.austin.pipeline.ProcessContext;
import com.ray.austin.service.api.impl.domain.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:12
 * @description 3.后置参数校验
 */

@Slf4j
@Service
public class AfterParamCheckAction implements BusinessProcess<SendTaskModel> {

    public static final String PHONE_REGEX_EXP = "^((13[0-9])|(14[5,7,9])|" +
            "(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\\\\d{8}$";
    public static final String EMAIL_REGEX_EXP = "^[A-Za-z0-9-_\\\\u4e00-\\\\u9fa5]+" +
            "@[a-zA-Z0-9_-]+(\\\\.[a-zA-Z0-9_-]+)+$";

    public static final HashMap<Integer, String> CHANNEL_REGEX_EXP = new HashMap<>();
    static {
        // 将发送类型编码和对应的正则表达式建立映射
        CHANNEL_REGEX_EXP.put(IdType.PHONE.getCode(), PHONE_REGEX_EXP);
        CHANNEL_REGEX_EXP.put(IdType.EMAIL.getCode(), EMAIL_REGEX_EXP);
    }
    @Override
    public void process(ProcessContext<SendTaskModel> context) {

        SendTaskModel sendTaskModel = context.getProcessModel();
        List<TaskInfo> taskInfo = sendTaskModel.getTaskInfo();
        // 1.过滤掉不合法的手机号、邮箱
        filterIllegalReceiver(taskInfo);
        if (CollUtil.isEmpty(taskInfo)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
        }
    }

    /**
     * 如果指定类型是手机,则检测输入的手机号是否合法
     * 如果指定类型是邮箱,则检测输入的邮箱是否合法
     * @param taskInfo
     */
    private void filterIllegalReceiver(List<TaskInfo> taskInfo) {
        // 获取taskInfo的第一个参数，即messageTemplateId模板id，获得对应模板的IdType
        Integer idType = CollUtil.getFirst(taskInfo.iterator()).getIdType();
        // 通过idType获取对应映射->正则表达式，并与taskInfo列表作为参数传递给filter
        filter(taskInfo, CHANNEL_REGEX_EXP.get(idType));

    }

    /**
     * 利用正则表达式过滤掉不合法的接收者
     * @param taskInfo
     * @param regexExp 正则表达式
     */
    private void filter(List<TaskInfo> taskInfo, String regexExp) {
        Iterator<TaskInfo> iterator = taskInfo.iterator();
        while (iterator.hasNext()){
            TaskInfo task = iterator.next();
            // 获取所有不匹配的接受者的列表
            Set<String> illegalPhone = task.getReceiver().stream()
                    .filter(phone -> !ReUtil.isMatch(regexExp, phone))
                    .collect(Collectors.toSet());
            if (CollUtil.isNotEmpty(illegalPhone)){
                // 从taskInfo的Receiver中去除不合法的名单
                task.getReceiver().removeAll(illegalPhone);
                log.error("messageTemplateId:{} find illegal receiver{}",
                        task.getMessageTemplateId(), JSON.toJSONString(illegalPhone));
            }
            // 若task内没有接收者，将其从taskInfo列表中删除
            if (CollUtil.isEmpty(task.getReceiver())){
                iterator.remove();
            }
        }
    }
}
