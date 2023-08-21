package com.ray.austin.discard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ray.austin.common.enums.constant.CommonConstant;
import com.ray.austin.common.domain.AnchorInfo;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.service.ConfigService;
import com.ray.austin.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/14 16:56
 */

@Service
public class DiscardMessageService {

    private static final String DISCARD_MESSAGE_KEY = "discardMsgIds";

    @Autowired
    private ConfigService configService;

    @Autowired
    private LogUtils logUtils;

    /**
     * 丢弃消息，配置在apollo
     * @param taskInfo
     * @return
     */
    public boolean isDiscard(TaskInfo taskInfo){
        //配置示例 ["1", "2"]
        JSONArray array = JSON.parseArray(configService.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_JSON_OBJECT));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))){
            // 进行链路追踪 打点
            logUtils.print(AnchorInfo.builder()
                    .businessId(taskInfo.getBusinessId())
                    .ids(taskInfo.getReceiver())
                    .state(AnchorState.DISCARD.getCode())
                    .build());
            return true;
        }
        return false;
    }
}
