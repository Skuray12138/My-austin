package com.ray.austin.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/7/30 10:45
 * 消息参数:接受者、占位符替换、扩展参数
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageParam {

    /**
     * 接受者
     * 多个使用 用 , 分隔
     * 不能大于100个
     * 必传参数
     */
    private String receiver;

    /**
     * 消息内容中的可变部分(占位符替换)
     * 可选
     */
    private Map<String, String> variables;

    /**
     * 扩展参数
     * 可选
     */
    private Map<String, String> extra;
}
