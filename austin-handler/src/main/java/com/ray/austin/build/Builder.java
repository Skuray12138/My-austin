package com.ray.austin.build;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.deduplication.DeduplicationParam;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:18
 * 去重参数构造器
 */

public interface Builder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    /**
     * 根据配置 构建去重参数
     * @param deduplication
     * @param taskInfo
     * @return
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);

}
