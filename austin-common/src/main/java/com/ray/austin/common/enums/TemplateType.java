package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/8/19 16:04
 * 模板类型枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum TemplateType implements PowerfulEnum{
    /**
     * 定时类模板(后台定时调用)
     */
    CLOCKING(10, "定时类模板(后台定时调用)"),

    /**
     * 实时类模板(接口实时调用)
     */
    REALTIME(20, "实时类模板(接口实时调用)");


    private final Integer code;
    private final String description;
}
