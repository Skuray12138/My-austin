package com.ray.austin.pipeline;

import com.ray.austin.common.vo.BasicResultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Skuray
 * @Date 2023/8/1 13:37
 * 责任链上下文，执行过程中的上下文
 * @Attribute 责任链code标识、责任链中断标识、存储责任链上下文数据模型、流程处理结果Result
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProcessContext<T extends ProcessModel>{

    /**
     * 标识责任链的code
     */
    private String code;

    /**
     * 存储责任链上下文的数据模型
     */
    private T processModel;

    /**
     * 责任链中断标识
     */
    private Boolean needBreak;

    /**
     * 流程处理结果
     */
    BasicResultVO response;
}
