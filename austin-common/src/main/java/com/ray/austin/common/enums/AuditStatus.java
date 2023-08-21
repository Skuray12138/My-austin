package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/8/19 15:52
 * 审计状态枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum AuditStatus implements PowerfulEnum{
    WAIT_AUDIT(10, "待审核"),
    AUDIT_SUCCESS(20,"审核成功"),
    AUDIT_REJECT(30,"被拒绝");

    private final Integer code;
    private final String description;

}
