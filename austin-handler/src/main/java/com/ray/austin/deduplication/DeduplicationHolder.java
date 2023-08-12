package com.ray.austin.deduplication;

import com.ray.austin.build.Builder;
import com.ray.austin.service.DeduplicationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:19
 * 去重类型 分别与 去重参数构造器、去重服务 的映射put与get
 */
@Service
public class DeduplicationHolder {

    /**
     * 将去重类型(deduplicationType)与去重参数构造器 进行映射
     */
    private final Map<Integer, Builder> builderHolder = new HashMap<>(4);

    /**
     * 将去重类型 与 去重服务 进行映射
     */
    private final Map<Integer, DeduplicationService> serviceHolder = new HashMap<>(4);

    /**
     * 根据去重类型找去重参数构造器
     * @param key
     * @return
     */
    public Builder selectBuilder(Integer key){
        return builderHolder.get(key);
    }

    /**
     * 根据去重类型找去重服务
     * @param key
     * @return
     */
    public DeduplicationService selectService(Integer key){
        return serviceHolder.get(key);
    }

    /**
     * 去重类型、去重参数构造器 存入映射
     * @param key
     * @param builder
     */
    public void putBuilder(Integer key, Builder builder){
        builderHolder.put(key, builder);
    }

    /**
     * 去重类型、去重服务 存入映射
     * @param key
     * @param service
     */
    public void putService(Integer key, DeduplicationService service){
        serviceHolder.put(key, service);
    }


}
