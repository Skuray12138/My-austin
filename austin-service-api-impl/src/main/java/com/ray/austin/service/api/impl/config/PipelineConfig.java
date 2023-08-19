package com.ray.austin.service.api.impl.config;

import com.ray.austin.pipeline.ProcessController;
import com.ray.austin.pipeline.ProcessTemplate;
import com.ray.austin.service.api.enmus.BusinessCode;
import com.ray.austin.service.api.impl.action.AfterParamCheckAction;
import com.ray.austin.service.api.impl.action.AssembleAction;
import com.ray.austin.service.api.impl.action.PreParamCheckAction;
import com.ray.austin.service.api.impl.action.SendMqAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:10
 * api层的pipeline配置类
 * @description 组装业务执行模板，将责任链code与执行模板构成映射关系
 */
@Configuration
public class PipelineConfig {
    @Autowired
    private PreParamCheckAction preParamCheckAction;
    @Autowired
    private AssembleAction assembleAction;
    @Autowired
    private AfterParamCheckAction afterParamCheckAction;
    @Autowired
    private SendMqAction sendMqAction;

    /**
     * 组装业务执行器，将发送前预执行流程通过责任链模式串联，生成业务执行模板
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate(){
        // 生成业务执行器列表
        ProcessTemplate processTemplate = new ProcessTemplate();
        // 将具体业务执行器 加入到 业务执行器列表
        processTemplate.setProcessList(Arrays.asList(preParamCheckAction,assembleAction,afterParamCheckAction,sendMqAction));
        return processTemplate;
    }

    /**
     * 将业务执行器列表与责任链code结合成映射形态，赋给ProcessController里的Map
     * @return
     */
    @Bean
    public ProcessController processController(){
        // 生成流程控制器
        ProcessController processController = new ProcessController();
        // 模板映射大小为4
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        // 将责任链code和业务执行模板进行映射赋值
        // 普通发送(code = send) -> 具体的业务执行列表(precessTemplate)
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        // 将templateConfig填充到processController里的templateConfig中
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

}
