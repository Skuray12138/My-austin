package xxl.service;

/**
 * @Author Skuray
 * @Date 2023/8/24 17:06
 * 具体处理定时任务逻辑的Handler
 */

public interface TaskHandler {

    /**
     * 处理的具体逻辑
     * @param messageTemplateId
     */
    void handle(Long messageTemplateId);
}
