package com.ray.austin.pending;

import com.dtp.core.thread.DtpExecutor;
import com.ray.austin.config.HandlerThreadPoolConfig;
import com.ray.austin.utils.GroupIdMappingUtils;
import com.ray.austin.utils.ThreadPoolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Author Skuray
 * @Date 2023/8/6 17:12
 * 存储 每种消息类型 与 TaskPending的关系
 */
@Component
public class TaskPendingHolder {
    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    /**
     * 将groupId和线程池executor映射起来
     */
    private Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);

    // 获取所有的groupId
    private static List<String> groupIds = GroupIdMappingUtils.getAllGroups();

    /**
     * 给每个渠道，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init(){
        for (String groupId : groupIds) {
            // 创建线程池
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            // 线程池加载到动态线程池
            threadPoolUtils.register(executor);
            // 保存映射
            taskPendingHolder.put(groupId, executor);
        }
    }

    /**
     * 通过groupId获得对应线程池
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId){
        return taskPendingHolder.get(groupId);
    }

}
