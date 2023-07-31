package com.ray.austin.common.dto.model;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:31
 * Email类型 发送模板
 */

public class EmailContentModel extends ContentModel{

    /**
     * 标题
     */
    private String title;

    /**
     * 内容 可写入HTML
     */
    private String content;
}
