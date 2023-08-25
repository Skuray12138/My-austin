package com.ray.austin.shield.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ray.austin.common.domain.AnchorInfo;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.common.enums.ShieldType;
import com.ray.austin.shield.ShieldService;
import com.ray.austin.utils.LogUtils;
import com.ray.austin.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @Author Skuray
 * @Date 2023/8/26 0:33
 * 屏蔽服务
 */
@Service
@Slf4j
public class ShieldServiceImpl implements ShieldService {

    private static final String NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY = "night_shield_send";

    private static final long SECONDS_OF_A_DAY = 86400L;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private LogUtils logUtils;

    @Override
    public void shield(TaskInfo taskInfo) {
        // 夜间不屏蔽
        if (ShieldType.NIGHT_NO_SHIELD.getCode().equals(taskInfo.getShieldType())){
            return;
        }
        //夜间屏蔽
        if (isNight()){
            // 夜间屏蔽，夜间消息被丢弃
            if (ShieldType.NIGHT_SHIELD.getCode().equals(taskInfo.getShieldType())){
                logUtils.print(AnchorInfo.builder()
                        .state(AnchorState.NIGHT_SHIELD.getCode()) //具体点位
                        .bizId(taskInfo.getBizId()) //业务消息发送id
                        .messageId(taskInfo.getMessageId())  //消息唯一id
                        .businessId(taskInfo.getBusinessId()) //业务id
                        .ids(taskInfo.getReceiver()) //要发送的用户
                        .build());
            }
            // 夜间屏蔽,且次日9点推送
            if (ShieldType.NIGHT_SHIELD_BUT_NEXT_DAY_SEND.getCode().equals(taskInfo.getShieldType())){
                //将推送信息存储在redis中,并设置过期时间为24小时
                redisUtils.lPush(NIGHT_SHIELD_BUT_NEXT_DAY_SEND_KEY,
                        JSON.toJSONString(taskInfo, SerializerFeature.WriteClassName), SECONDS_OF_A_DAY);
                logUtils.print(AnchorInfo.builder()
                        .state(AnchorState.NIGHT_SHIELD_NEXT_SEND.getCode())
                        .bizId(taskInfo.getBizId())
                        .messageId(taskInfo.getMessageId())
                        .businessId(taskInfo.getBusinessId())
                        .ids(taskInfo.getReceiver())
                        .build());

            }
            taskInfo.setReceiver(new HashSet<>());
        }

    }

    /**
     * 小时 < 8 就默认是凌晨
     * @return
     * true: 夜间
     * false: 非夜间
     */
    private boolean isNight() {
        return LocalDateTime.now().getHour() < 8;
    }
}
