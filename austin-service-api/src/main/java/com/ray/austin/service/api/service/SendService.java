package com.ray.austin.service.api.service;

import com.ray.austin.service.api.domain.BatchSendRequest;
import com.ray.austin.service.api.domain.SendRequest;
import com.ray.austin.service.api.domain.SendResponse;

/**
 * @Author Skuray
 * @Date 2023/7/30 22:54
 * 发送接口
 */

public interface SendService {

    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);

    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);
}
