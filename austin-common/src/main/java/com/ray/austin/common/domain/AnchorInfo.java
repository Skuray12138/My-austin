package com.ray.austin.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @Author Skuray
 * @Date 2023/8/19 10:16
 * 埋点信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnchorInfo {

    /**
     * 业务消息发送id(数据追踪用)
     * 生成逻辑：TaskInfoUtils
     */
    private String bizId;

    /**
     * 消息唯一id(数据追踪用)
     */
    private String messageId;

    /**
     * 发送用户
     */
    private Set<String> ids;

    /**
     * 具体点位:在 com.ray.austin.common.enums.AnchorState中
     */
    private int state;

    /**
     * 业务Id(数据追踪使用)
     * 生成逻辑: TaskInfoUtils
     */
    private Long businessId;

    /**
     * 日志生成时间
     */
    private long logTimestamp;
}
