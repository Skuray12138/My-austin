package com.ray.austin.service.api.impl.action;

import com.ray.austin.pipeline.BusinessProcess;
import com.ray.austin.pipeline.ProcessContext;
import com.ray.austin.service.api.impl.domain.SendTaskModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Skuray
 * @Date 2023/8/1 21:12
 * @description 3.后置参数校验
 */

@Slf4j
@Service
public class AfterParamCheckAction implements BusinessProcess<SendTaskModel> {
    @Override
    public void process(ProcessContext<SendTaskModel> context) {

    }
}
