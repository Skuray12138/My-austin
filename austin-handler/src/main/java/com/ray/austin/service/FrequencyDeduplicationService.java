package com.ray.austin.service;

import cn.hutool.core.util.StrUtil;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.DeduplicationType;
import com.ray.austin.limit.LimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:22
 * 频次去重服务 <p>
 * 普通去重
 */
@Service
public class FrequencyDeduplicationService extends AbstractDeduplicationService{

    @Autowired
    public FrequencyDeduplicationService(@Qualifier("SimpleLimitService")LimitService limitService){
        this.limitService = limitService;
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    private static final String PREFIX = "FRE";

    /**
     * 频次去重 构建key <p>
     * key: FRE_receiver_templateId_sendChannel <p>
     * 一天内 一个用户只能收到某个渠道的消息N次
     * @param taskInfo
     * @param receiver
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrUtil.C_UNDERLINE
                + receiver + StrUtil.C_UNDERLINE
                + taskInfo.getMessageTemplateId() + StrUtil.C_UNDERLINE
                + taskInfo.getSendChannel();
    }
}
