package com.ray.austin.mq.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.constans.MessageQueuePipeline;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/3 18:01
 * 发送给mq实现类. 将消息发送给eventbus消息队列
 */

@Slf4j
@Service
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
//配置文件配置eventbus消息队列时才加载
public class EventBusSendMqServiceImpl implements SendMqService {

    private EventBus eventBus = new EventBus();

    @Autowired
    private EventBusListener eventBusListener;
    @Value("${austin.business.topic.name}")
    private String sendTopic;
    @Value("${austin.business.recall.name}")
    private String recallTopic;

    /**
     * 单机 队列默认不支持 tagId过滤(单机无必要)
     * @param topic
     * @param jsonValue
     * @param tagId
     */
    @Override
    public void send(String topic, String jsonValue, String tagId) {

        eventBus.register(eventBusListener);
        // 如果是send发送任务，则传递TaskInfo; 否则是recall任务，传递MessageTemplate
        if (topic.equals(sendTopic)){
            // JSON.parseArray: 将jsonValue里的数据，参照TaskInfo类，将json字符串转换成一个个实体类。多个对象构成List集合
            eventBus.post(JSON.parseArray(jsonValue, TaskInfo.class));
        } else if (topic.equals(recallTopic)){
            eventBus.post(JSON.parseObject(jsonValue, MessageTemplate.class));
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
