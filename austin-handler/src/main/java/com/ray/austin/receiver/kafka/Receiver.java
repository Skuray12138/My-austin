package com.ray.austin.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.constans.MessageQueuePipeline;
import com.ray.austin.receiver.service.ConsumeService;
import com.ray.austin.utils.GroupIdMappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Author Skuray
 * @Date 2023/8/5 23:34
 * 消费MQ的消息 <p>
 * 由消息队列kafka来消费
 * @Scope (SCOPE_PROTOTYPE)多实例,IOC容器启动不会调用方法创建对象,只有在获取时才会创建对象; <p>
 *        SCOPE_SINGLETON,单实例,IOC启动时就会调用方法将对象放入IOC容器中
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.KAFKA)
public class Receiver {
    @Autowired
    private ConsumeService consumeService;

    /**
     * 发送消息
     * @param consumerRecord
     * @param topicGroupId
     */
    @KafkaListener(topics = "#{'${austin.business.topic.name}'}", containerFactory = "filterContainerFactory")
    public void consumer(ConsumerRecord<?, String> consumerRecord, @Header(KafkaHeaders.GROUP_ID) String topicGroupId){
        // Optional.ofNullable准许入参可以为null
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        // Optional.isPresent 用于标识Optional是否封装了一个值，只有封装了值才能进行后续操作
        if (kafkaMessage.isPresent()){
            // 将kafka消息转为TaskInfo类型的集合
            List<TaskInfo> taskInfoLists = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            // 拿到集合第一个元素，拼装其消费者组groupId
            String messageGroupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
            if (topicGroupId.equals(messageGroupId)){
                log.info("groupId:{},params:{}", messageGroupId, JSON.toJSONString(taskInfoLists));
                consumeService.consume2Send(taskInfoLists);
            }
        }
    }

    /**
     * 撤回消息
     * @param consumerRecord
     */
    @KafkaListener(topics = "#{'${austin.business.recall.topic.name}'}",
            groupId = "#{'${austin.business.recall.group.name}'}",
            containerFactory = "filterContainerFactory")
    public void recall(ConsumerRecord<?, String> consumerRecord){

    }
}
