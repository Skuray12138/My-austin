package com.ray.austin.deduplication;

import com.alibaba.fastjson.annotation.JSONField;
import com.ray.austin.common.domain.TaskInfo;
import com.ray.austin.common.enums.AnchorState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/8/9 15:20
 * 去重服务所需要的参数:TaskInfo、去重时间、多少次数后去重、去重埋点
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeduplicationParam {

    /**
     * TaskInfo信息
     */
    private TaskInfo taskInfo;

    /**
     * 去重时间 -- 单位(秒)
     */
    @JSONField(name = "time")
    private Long deduplicationTime;

    /**
     * 需要达到的次数去重
     */
    @JSONField(name = "num")
    private Integer countNum;

    /**
     * 标识数据哪种去重(数据埋点)
     */
    private AnchorState anchorState;
}
