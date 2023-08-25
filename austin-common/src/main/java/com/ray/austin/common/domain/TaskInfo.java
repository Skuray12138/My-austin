package com.ray.austin.common.domain;

import com.ray.austin.common.dto.model.ContentModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:23
 * 发送任务消息包装类
 * @Attribute 消息模板id、业务id、接受者、发送id类型、发送渠道、模板类型、消息类型、屏蔽类型、文案模型、发送账号
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskInfo {

    /**
     * 业务消息发送id(数据追踪用)。若不存在，则使用messageId
     */
    private String bizId;

    /**
     * 消息唯一id(数据追踪用)
     */
    private String messageId;


    /**
     * 消息模板id
     */
    private Long messageTemplateId;

    /**
     * 业务id (追踪数据使用)
     * 生成逻辑 TaskInfoUtils
     */
    private Long businessId;

    /**
     * 接收者
     */
    private Set<String> receiver;

    /**
     * 发送的Id类型
     */
    private Integer idType;

    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 屏蔽类型
     */
    private Integer shieldType;

    /**
     * 发送文案模型
     * message_template表存储的content是JSON （所有内容都塞进去）
     * 不同的渠道要发送的内容不同（比如push会有img，而短信没有）
     */
    private ContentModel contentModel;

    /**
     * 发送账号（邮件下可能有多个发送账号、短信可有多个发送账号）
     */
    private Integer sendAccount;


}
