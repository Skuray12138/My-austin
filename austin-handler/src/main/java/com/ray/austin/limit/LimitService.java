package com.ray.austin.limit;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.deduplication.DeduplicationParam;
import com.ray.austin.service.AbstractDeduplicationService;

import javax.swing.text.TabSet;
import java.util.Set;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:21
 * 去重限制
 */

public interface LimitService {

    /**
     * 去重限制，筛选符合去重限制的对象
     * @param service 去重器对象
     * @param taskInfo
     * @param param 去重参数
     * @return 不符合条件的receiver: phoneNumber、email...
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);
}
