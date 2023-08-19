package com.ray.austin.handler;

import com.ray.austin.common.domain.AnchorInfo;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Author Skuray
 * @Date 2023/8/18 10:53
 * 发送各个渠道的handler
 */

public abstract class BaseHandler implements Handler{

    @Autowired
    private HandlerHolder handlerHolder;

    @Autowired
    private LogUtils logUtils;

    /**
     * 标识渠道的code <p>
     * 子类初始化时指定
     */
    protected Integer channelCode;

    /**
     * 初始化：渠道 与 channelCode的映射关系
     */
    @PostConstruct
    private void init(){
        handlerHolder.putHandler(channelCode, this);
    }

    @Override
    public void doHandler(TaskInfo taskInfo) {
        if (handler(taskInfo)){
            // 发送成功 打点
            logUtils.print(AnchorInfo.builder()
                    .businessId(taskInfo.getBusinessId())
                    .ids(taskInfo.getReceiver())
                    .state(AnchorState.SEND_SUCCESS.getCode())
                    .build());
            return;
        }
        // 发送失败 打点
        logUtils.print(AnchorInfo.builder()
                .businessId(taskInfo.getBusinessId())
                .ids(taskInfo.getReceiver())
                .state(AnchorState.SEND_FAIL.getCode())
                .build());
    }

    /**
     * 统一处理的handler接口
     * @param taskInfo
     * @return
     */
    public abstract boolean handler(TaskInfo taskInfo);


}
