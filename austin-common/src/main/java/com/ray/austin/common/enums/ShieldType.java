package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/8/26 0:19
 * 屏蔽 类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum ShieldType implements PowerfulEnum{

    /**
     * 模板设置为夜间不屏蔽
     */
    NIGHT_NO_SHIELD(10, "夜间不屏蔽"),

    /**
     * 模板设置为夜间屏蔽 -- 凌晨接收到的消息将会被过滤掉
     */
    NIGHT_SHIELD(20, "夜间不屏蔽"),

    /**
     * 模板设置为夜间屏蔽(次日早上9点发送) -- 凌晨接收到的消息将会在次日发送
     */
    NIGHT_SHIELD_BUT_NEXT_DAY_SEND(30, "夜间屏蔽(次日早上9点发送)");


    private final Integer code;
    private final String description;

}
