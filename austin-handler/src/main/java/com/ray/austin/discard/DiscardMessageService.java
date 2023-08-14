package com.ray.austin.discard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ray.austin.common.constant.CommonConstant;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.service.ConfigService;
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

    /**
     * 丢弃消息，配置在apollo
     * @param taskInfo
     * @return
     */
    public boolean isDiscard(TaskInfo taskInfo){
        //配置示例 ["1", "2"]
        JSONArray array = JSON.parseArray(configService.getProperty(DISCARD_MESSAGE_KEY, CommonConstant.EMPTY_JSON_OBJECT));
        if (array.contains(String.valueOf(taskInfo.getMessageTemplateId()))){
            return true;
        }
        return false;
    }
}
