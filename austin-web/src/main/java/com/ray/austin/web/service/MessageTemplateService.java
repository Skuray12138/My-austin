package com.ray.austin.web.service;

import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.web.vo.MessageTemplateParam;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/20 21:42
 * 消息模板管理接口
 */

public interface MessageTemplateService {

    /**
     * 查询未删除的模板列表(分页)
     * @param messageTemplateParam
     * @return
     */
    Page<MessageTemplate> queryList(MessageTemplateParam messageTemplateParam);

    /**
     * 统计未删除的条数
     * @return
     */
    Long count();

    /**
     * 单个保存或更新: 存在ID则更新、不存在ID则保存
     * @param messageTemplate
     * @return
     */
    MessageTemplate saveOrUpdate(MessageTemplate messageTemplate);

    /**
     * 软删除(deleted=1)
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询模板信息
     * @param id
     * @return
     */
    MessageTemplate queryById(Long id);

    /**
     * 复制配置
     * @param id
     */
    void copy(Long id);

    /**
     * 启动模板的定时任务
     * @param id
     * @return
     */
    BasicResultVO startCronTask(Long id);

    /**
     * 暂停模板的定时任务
     * @param id
     * @return
     */
    BasicResultVO stopCronTask(Long id);

}
