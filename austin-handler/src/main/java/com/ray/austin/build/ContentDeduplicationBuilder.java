package com.ray.austin.build;

import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import com.ray.austin.common.enums.DeduplicationType;
import com.ray.austin.deduplication.DeduplicationParam;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:19
 * 5分钟相同内容去重 参数构造器
 */
@Service
public class ContentDeduplicationBuilder extends AbstractDeduplicationBuilder implements Builder {

    public ContentDeduplicationBuilder(){ // 给父类的属性DeduplicationType赋值是哪一类去重
        deduplicationType = DeduplicationType.CONTENT.getCode();
    }

    /**
     * 构建 5分钟相同内容去重 所需要的参数
     * @param deduplication
     * @param taskInfo
     * @return
     */
    @Override
    public DeduplicationParam build(String deduplication, TaskInfo taskInfo) {
        // 在getParamsFromConfig中创建了deduplicationParam对象，并存入了taskInfo
        DeduplicationParam deduplicationParam = getParamsFromConfig(deduplicationType, deduplication, taskInfo);
        if (Objects.isNull(deduplicationParam)){
            return null;
        }
        deduplicationParam.setAnchorState(AnchorState.CONTENT_DEDUPLICATION); // 打点
        return deduplicationParam;
    }
}
