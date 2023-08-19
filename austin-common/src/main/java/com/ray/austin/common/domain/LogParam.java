package com.ray.austin.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/8/19 10:20
 * 日志参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogParam {
    /**
     * 需要记录的日志
     */
    private Object object;

    /**
     * 标识日志的业务
     */
    private String bizType;

    /**
     * 生成时间
     */
    private long timestamp;
}
