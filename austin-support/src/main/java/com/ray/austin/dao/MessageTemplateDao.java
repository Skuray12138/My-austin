package com.ray.austin.dao;

import com.ray.austin.domain.MessageTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/7/29 18:08
 * 消息模板Dao
 */

public interface MessageTemplateDao extends JpaRepository<MessageTemplate,Long> {

    /**
     * 查询 列表分页
     * @param deleted 0: 未删除    1: 已删除
     * @param pageable 分页对象
     * @return
     */
    List<MessageTemplate> findAllByIsDeletedEquals(Integer deleted, Pageable pageable);

    /**
     * 统计未删除的条数
     * @param deleted
     * @return
     */
    Long countByIsDeletedEquals(Integer deleted);
}
