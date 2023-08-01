package com.ray.austin.pipeline;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/1 16:06
 * @description 业务执行模板（将责任链的逻辑串联起来）
 * 将具体的实现类串联起来
 * @Attribute 存放BusinessProcess的List列表
 * @Method getter setter
 */

public class ProcessTemplate {
    private List<BusinessProcess> processList;

    public List<BusinessProcess> getProcessList() {
        return processList;
    }

    public void setProcessList(List<BusinessProcess> processList) {
        this.processList = processList;
    }
}
