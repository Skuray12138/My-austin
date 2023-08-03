package com.ray.austin.common.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/8/2 10:25
 * 官方账号(如公众号)发送消息模板
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfficialAccountsContentModel extends ContentModel{

    /**
     * 消息模板发送的数据
     */
    Map<String, String> map;

    /**
     * 消息模板跳转的url
     */
    String url;
}
