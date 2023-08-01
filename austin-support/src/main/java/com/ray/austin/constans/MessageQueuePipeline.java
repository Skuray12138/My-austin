package com.ray.austin.constans;

/**
 * @Author Skuray
 * @Date 2023/7/31 21:15
 * 消息队列常量
 */

public interface MessageQueuePipeline {

    String EVENT_BUS = "eventBus";

    String KAFKA = "kafka";

    String RABBIT_MQ = "rabbitMq";

    String ROCKET_MQ = "rocketMq";
}
