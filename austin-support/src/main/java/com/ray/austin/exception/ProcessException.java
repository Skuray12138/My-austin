package com.ray.austin.exception;

import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.pipeline.ProcessContext;

/**
 * @Author Skuray
 * @Date 2023/8/1 13:35
 * @discription 自定义异常类
 * 创建异常类时，丢入异常信息
 */

public class ProcessException extends RuntimeException{

    /**
     * 流程处理中的上下文
     */
    private final ProcessContext processContext;

    public ProcessException(ProcessContext processContext) {
        super();
        this.processContext = processContext;
    }

    public ProcessException(ProcessContext processContext, Throwable cause){
        super(cause);
        this.processContext = processContext;
    }

    /**
     * 抛异常时会自动调用该方法 获取调用异常构造方法时输入的String s
     * @return
     */
    @Override
    public String getMessage() {
        if (this.processContext != null){
            // 上下文中异常信息不为空，则给出上下文中的异常信息
            return this.processContext.getResponse().getMsg();
        } else {
            // 上下文中异常信息为空，则给出错误信息"流程上下文为空"
            return RespStatusEnum.CONTEXT_IS_NULL.getMsg();
        }
    }

    public ProcessContext getProcessContext(){
        return processContext;
    }
}
