package com.ray.austin.build;


import com.alibaba.fastjson.JSONObject;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.deduplication.DeduplicationHolder;
import com.ray.austin.deduplication.DeduplicationParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:18
 * 抽象 去重参数构建器 <p>
 * 提供方法将TaskInfo放入去重参数类
 */

public abstract class AbstractDeduplicationBuilder implements Builder{

    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    /**
     * 将去重类型 与 对应去重参数构造器 对应起来
     */
    @PostConstruct
    public void init(){
        deduplicationHolder.putBuilder(deduplicationType, this);
    }

    /**
     * 从配置中 获取去重服务参数: 将TaskInfo存入deduplicationParam
     * @param key
     * @param duplicationConfig
     * @param taskInfo
     * @return
     */
    public DeduplicationParam getParamsFromConfig(Integer key, String duplicationConfig, TaskInfo taskInfo){
        JSONObject object = JSONObject.parseObject(duplicationConfig);
        if (Objects.isNull(object)){
            return null;
        }
        DeduplicationParam deduplicationParam = JSONObject.parseObject(object.getString(DEDUPLICATION_CONFIG_PRE + key),
                DeduplicationParam.class);

        if (Objects.isNull(deduplicationParam)){
            return null;
        }
        deduplicationParam.setTaskInfo(taskInfo);
        return deduplicationParam;
    }


}
