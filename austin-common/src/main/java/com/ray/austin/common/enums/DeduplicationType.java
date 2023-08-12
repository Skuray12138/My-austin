package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/7 21:01
 * 去重类型枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum DeduplicationType {
    /**
     * 相同内容去重
     */
    CONTENT(10, "N分钟相同内容去重"),

    /**
     * 渠道接收消息 频次去重
     */
    FREQUENCY(20, "一天内N次相同渠道去重"),
    ;

    private final Integer code;
    private final String description;

    /**
     * 获取去重渠道
     * @return 去重code列表
     */
    public static List<Integer> getDeduplicationList(){
        ArrayList<Integer> result = new ArrayList<>();
        for (DeduplicationType value : DeduplicationType.values()) {
            result.add(value.getCode());
        }
        return result;
    }
}
