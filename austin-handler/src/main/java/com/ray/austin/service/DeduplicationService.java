package com.ray.austin.service;

import com.ray.austin.deduplication.DeduplicationParam;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:22
 * 去重服务
 */

public interface DeduplicationService {
    /**
     * 去重
     * @param param
     */
    void deduplication(DeduplicationParam param);
}
