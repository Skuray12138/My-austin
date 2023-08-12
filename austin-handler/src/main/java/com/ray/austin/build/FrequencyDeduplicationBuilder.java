package com.ray.austin.build;

import cn.hutool.core.date.DateUtil;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.common.enums.DeduplicationType;
import com.ray.austin.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:19
 * 1天内相同渠道频次去重 参数构造器
 */
@Service
public class FrequencyDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder{

    public FrequencyDeduplicationBuilder(){
        deduplicationType = DeduplicationType.FREQUENCY.getCode();
    }

    /**
     * 构建 1天内相同渠道频次去重 所需要的参数
     * @param deduplication
     * @param taskInfo
     * @return
     */
    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)){
            return null;
        }

        // 设置去重时间 单位为秒
        // (当天结束的时间 - 现在的时间) / 1000
        deduplicationParam.setDeduplicationTime((DateUtil.endOfDay(new Date()).getTime() - DateUtil.current()) / 1000);
        deduplicationParam.setAnchorState(AnchorState.RULE_DEDUPLICATION); // 打点 消息频次去重

        return deduplicationParam;
    }
}
