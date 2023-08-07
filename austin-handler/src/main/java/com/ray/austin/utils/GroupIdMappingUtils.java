package com.ray.austin.utils;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.ChannelType;
import com.ray.austin.common.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/5 23:51
 * 使用groupId标识每一个消费者组
 */

public class GroupIdMappingUtils {

    /**
     * 获取所有的消费者组groups <p>
     * 不同渠道、不同消息类型，构成自己独有的groupId
     * @return
     */
    public static List<String> getAllGroups(){
        List<String> groupIds = new ArrayList<>();
        for (ChannelType channelType : ChannelType.values()) {
            for (MessageType messageType : MessageType.values()) {
                groupIds.add(channelType.getCodeEn() + "." + messageType.getCodeEn());
            }
        }
        return groupIds;
    }

    public static String getGroupIdByTaskInfo(TaskInfo taskInfo){
        // 通过taskInfo分别找出对应的channel和msg的codeEn，将他们拼接成groupId的格式
        String channelCodeEn = ChannelType.getEnumByCode(taskInfo.getSendChannel()).getCodeEn();
        String msgCodeEn = MessageType.getEnumByCode(taskInfo.getMsgType()).getCodeEn();
        return channelCodeEn + "." + msgCodeEn;
    }
}
