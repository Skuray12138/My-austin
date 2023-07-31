package com.ray.austin.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:35
 * push通知栏类型(通知栏消息推送类型) 发送模板
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PushContentModel extends ContentModel{

    private String title;

    private String content;

    private String url;
}
