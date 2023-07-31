package com.ray.austin.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:37
 * sms短信类型 模板
 * 前端填写时分开，最后处理时会将URL拼接在content上
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsContentModel extends ContentModel{

    /**
     * 短信发送内容
     */
    private String content;

    /**
     * 短信发送链接
     */
    private String url;
}
