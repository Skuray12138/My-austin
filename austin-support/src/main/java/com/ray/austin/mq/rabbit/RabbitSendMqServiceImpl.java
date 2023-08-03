package com.ray.austin.mq.rabbit;

import com.ray.austin.constans.MessageQueuePipeline;
import com.ray.austin.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/3 21:29
 * 发送给mq实现类. 将消息发送给rabbit消息队列
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.RABBIT_MQ)
public class RabbitSendMqServiceImpl implements SendMqService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${austin.rabbitmq.topic.name}")
    private String confTopic;
    @Value("${austin.rabbitmq.exchange.name}")
    private String exchangeName;

    @Override
    public void send(String topic, String jsonValue, String tagId) {
        if (topic.equals(confTopic)){
            rabbitTemplate.convertAndSend(exchangeName, confTopic, jsonValue);
        } else {
            log.error("RabbitSendMqServiceImpl send topic error! topic:{}, confTopic:{}", topic, confTopic);
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        send(topic, jsonValue, null);
    }
}
