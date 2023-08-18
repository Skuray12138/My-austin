package com.ray.austin.handler;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.domain.MessageTemplate;
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
            return;
        }
    }

    /**
     * 统一处理的handler接口
     * @param taskInfo
     * @return
     */
    public abstract boolean handler(TaskInfo taskInfo);


}
