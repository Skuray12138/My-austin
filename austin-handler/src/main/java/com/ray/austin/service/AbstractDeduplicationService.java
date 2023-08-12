package com.ray.austin.service;

import cn.hutool.core.collection.CollUtil;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.deduplication.DeduplicationHolder;
import com.ray.austin.deduplication.DeduplicationParam;
import com.ray.austin.limit.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:22
 * 抽象 去重服务
 */
@Slf4j
public abstract class AbstractDeduplicationService implements DeduplicationService{

    protected Integer deduplicationType;

    protected LimitService limitService;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    /**
     * 将 去重类型 与 去重服务 构成映射
     */
    @PostConstruct
    private void init(){
        deduplicationHolder.putService(deduplicationType, this);
    }

    /**
     * 去重并删除符合的receiver 从taskInfo
     * @param param
     */
    @Override
    public void deduplication(DeduplicationParam param) {
        TaskInfo taskInfo = param.getTaskInfo();
        // 去重限制，筛选符合去重条件的用户
        Set<String> filterReceiver = limitService.limitFilter(this, taskInfo, param);
        // 剔除符合去重条件的用户 删除taskInfo中符合去重条件的用户
        if (CollUtil.isNotEmpty(filterReceiver)){
            taskInfo.getReceiver().removeAll(filterReceiver);
        }
    }

    /**
     * 构建 去重的key
     * @param taskInfo
     * @param receiver
     * @return
     */
    public abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);
}
