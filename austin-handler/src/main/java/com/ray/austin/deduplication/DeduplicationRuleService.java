package com.ray.austin.deduplication;

import com.ray.austin.common.enums.constant.CommonConstant;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.DeduplicationType;
import com.ray.austin.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:20
 * 去重服务入口
 */
@Service
public class DeduplicationRuleService {

    public static final String DEDUPLICATION_RULE_KEY="deduplicationRule";

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Autowired
    private ConfigService configService;

    /**
     * 拿到去重服务列表，顺序执行两种去重服务，taskInfo作为参数传入去重服务
     * @param taskInfo
     */
    public void duplication(TaskInfo taskInfo){

        /**
         * 配置样例:
         * {"deduplication_10":{"num":1,"time":300},"deduplication_20":{"num":5}}
         * 可通过分布式配置中心来更新配置
         */
//        String deduplicationConfig = "{\"deduplication_10\":{\"num\":1,\"time\":300}," +
//                "\"deduplication_20\":{\"num\":5}}";
        String deduplicationConfig = configService.getProperty(DEDUPLICATION_RULE_KEY, CommonConstant.EMPTY_JSON_OBJECT);

        // 获取去重渠道code列表 10:内容去重  20:频次去重
        List<Integer> deduplicationList = DeduplicationType.getDeduplicationList();

        // 循环去重列表，两种去重都要进行:选择参数构造器->构建参数->选择去重服务->去重服务
        for (Integer deduplicationType : deduplicationList) {
            // 通过去重渠道选择对应的去重参数构造器
            // 通过配置 构建去重所需要的参数
            DeduplicationParam deduplicationParam = deduplicationHolder.selectBuilder(deduplicationType)
                    .build(deduplicationConfig, taskInfo);

            if (Objects.nonNull(deduplicationParam)){
                // 通过去重渠道选择对应的去重服务
                // 通过去重参数，构建合适的key，并进行去重
                deduplicationHolder.selectService(deduplicationType).deduplication(deduplicationParam);
            }
        }
        /**
         * 1. 构建去重参数param(根据去重渠道，选择对应的参数构造器(Builder))
         * 2. 构建key(根据param选择对应的Service)
         * 3. 判断Receiver是否满足去重条件(LimitService),若满足则加入filterReceiver中
         * 4. 删除taskInfo中满足去重条件的用户
         */
    }

}
