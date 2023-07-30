package com.ray.austin.service.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author Skuray
 * @Date 2023/7/30 11:30
 * 发送接口返回值: 响应状态、响应编码
 */

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SendResponse {

    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;
}
