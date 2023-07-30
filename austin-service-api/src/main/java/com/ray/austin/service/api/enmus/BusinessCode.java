package com.ray.austin.service.api.enmus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/7/30 11:32
 */

@Getter
@ToString
@AllArgsConstructor
public enum BusinessCode {

    /**
     * 普通发送流程
     */
    COMMON_SEND("send","普通发送"),

    /**
     * 撤回流程
     */
    RECALL("recall","撤回消息");

    /**
     * code用来关联责任链的模板
     */
    private String code;

    /**
     * 类型说明
     */
    private String description;



}
