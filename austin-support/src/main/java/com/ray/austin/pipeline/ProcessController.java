package com.ray.austin.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.exception.ProcessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/8/1 16:30
 * 流程控制器
 * @description 进行前置检查，并执行process
 * @Attribute 责任链code 和 业务模板的映射器Map
 */

@Data
@Slf4j
public class ProcessController {

    /**
     * 模板映射 <p>
     * 不同业务有不同的责任链code，将code和业务执行模板进行映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;

    /**
     * 执行责任链
     * @param context
     * @return 返回上下文内容context
     */
    public ProcessContext process(ProcessContext context){

        /**
         * 前置检查
         */
        try {
            preCheck(context);
        } catch (ProcessException e) {
            return e.getProcessContext();
        }
        // 获取业务执行器列表
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        // 遍历业务执行器列表，执行具体的业务执行器的process的逻辑
        for (BusinessProcess businessProcess : processList){
            businessProcess.process(context);
            if (context.getNeedBreak()){
                break;
            }
        }
        return context;
    }

    /**
     * @description 前置检查，出问题抛出异常 <p>
     * 1.流程上下文是否为空 <p>
     * 2.业务代码是否为空 <p>
     * 3.流程模板是否为空 <p>
     * 4.业务处理器列表(BusinessProcess的List列表)是否为空
     * @param context
     */
    private void preCheck(ProcessContext context) {
        // 上下文 为空，返回失败消息
        if (context == null){
            context = new ProcessContext();
            // 设置响应编码
            context.setResponse(BasicResultVO.fail(RespStatusEnum.CONTEXT_IS_NULL));
            throw new ProcessException(context);
        }

        // 业务代码为空
        String businessCode = context.getCode();
        if (StrUtil.isBlank(businessCode)){
            context.setResponse(BasicResultVO.fail(RespStatusEnum.BUSINESS_CODE_IS_NULL));
            throw new ProcessException(context);
        }

        // 执行模板为空
        ProcessTemplate processTemplate = templateConfig.get(businessCode);
        if (processTemplate == null){
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_TEMPLATE_IS_NULL));
            throw new ProcessException(context);
        }

        // 业务处理器列表为空
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)){
            context.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_LIST_IS_NULL));
            throw new ProcessException(context);
        }

    }
}
