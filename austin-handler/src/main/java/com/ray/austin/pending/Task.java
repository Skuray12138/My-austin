package com.ray.austin.pending;

import cn.hutool.core.collection.CollUtil;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.deduplication.DeduplicationRuleService;
import com.ray.austin.discard.DiscardMessageService;
import com.ray.austin.handler.HandlerHolder;
import com.ray.austin.service.DeduplicationService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author Skuray
 * @Date 2023/8/6 17:07
 * Task执行器 <p>
 * 1.丢弃消息 <p>
 * 2.屏蔽消息 <p>
 * 3.通用去重功能 <p>
 * 4.发送消息
 *
 */
@Data
@Accessors(chain = true)
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{

    private TaskInfo taskInfo;

    @Autowired
    DeduplicationRuleService deduplicationRuleService;

    @Autowired
    private DiscardMessageService discardMessageService;

    @Autowired
    private HandlerHolder handlerHolder;

    @Override
    public void run() {
        // 1.丢弃消息
        if (discardMessageService.isDiscard(taskInfo)){
            return;
        }
        // 2.屏蔽消息

        // 3.通用去重功能
        if (CollUtil.isNotEmpty(taskInfo.getReceiver())){
            deduplicationRuleService.duplication(taskInfo);
        }

        // 4.发送消息
        if (CollUtil.isNotEmpty(taskInfo.getReceiver())){
            handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
        }
    }
}
