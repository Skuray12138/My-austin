package com.ray.austin.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:31
 * Email类型 发送模板
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentModel extends ContentModel{

    /**
     * 标题
     */
    private String title;

    /**
     * 内容 可写入HTML
     */
    private String content;

    /**
     * 邮件附件链接
     */
    private String url;
}
