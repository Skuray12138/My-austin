package com.ray.austin.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @Author Skuray
 * @Date 2023/8/21 10:19
 * 消息模板的vo  <p>
 * vo: 通常用于在前端显示数据  <p>
 * po: 用于表示数据库结构相对应的Java对象  <p>
 * dto: 用于在不同层之间传输数据
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplateVo {

    /**
     * 返回List列表
     */
    private List<Map<String, Object>> rows;

    /**
     * 总条数
     */
    private Long count;

}
