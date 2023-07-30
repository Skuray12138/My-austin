package com.ray.austin.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Skuray
 * @Date 2023/7/30 11:15
 * 发送/撤回接口的参数: 业务类型、消息模板id、消息相关参数
 * @Accessors(chain = true) 开启链式编程，可直接user.setId("123").setAge(17).setName("Alice")
 *                          同时使该类中setter方法的返回值是自身this,代替了void
 *                          即上面user的set操作中，set后返回自身到user
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendRequest {

    /**
     * 执行业务类型
     * send: 发送
     * recall: 撤回
     */
    private String code;

    /**
     * 消息模板id
     * 必填
     */
    private Long messageTemplateId;

    /**
     * 消息相关参数
     * 当业务类型为send时，必填
     */
    private MessageParam messageParam;
}
