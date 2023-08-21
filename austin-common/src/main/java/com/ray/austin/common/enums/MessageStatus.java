package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/8/19 15:58
 * 消息状态枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageStatus implements PowerfulEnum {

    INIT(10, "初始化状态"),
    STOP(20, "停用"),
    RUN(30, "启用"),
    PENDING(40, "等待发送"),
    SENDING(50, "发送中"),
    SEND_SUCCESS(60, "发送成功"),
    SEND_FAIL(70, "发送失败");

    private final Integer code;
    private final String description;
}
