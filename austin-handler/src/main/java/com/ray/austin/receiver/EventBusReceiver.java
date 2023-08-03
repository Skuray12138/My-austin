package com.ray.austin.receiver;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.constans.MessageQueuePipeline;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.mq.eventbus.EventBusListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/2 10:34
 * @Component 让这个类由spring管理
 * @ConditionalOnProperty 从配置文件中读取name指定的某个属性值，不为空则将该值与havingValue的值进行比较，一样则返回true。
 * 此时该配置类中的Component注解生效，否则不生效 <p>
 * 本类中就需要有  austin.mq.pipeline = eventBus 配置存在，否则该类不会生效被spring管理
 * @description 对于消息队列eventBus的消费消息、撤回消息方法
 */
@Component
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.EVENT_BUS)
@Slf4j
public class EventBusReceiver implements EventBusListener {
    @Override
    @Subscribe   // @Subscribe注解使该方法可以接收消息队列eventBus发出的消息
    public void consume(List<TaskInfo> lists) {
        log.error(JSON.toJSONString(lists));
    }

    @Override
    @Subscribe
    public void recall(MessageTemplate messageTemplate) {

    }
}
