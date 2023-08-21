package com.ray.austin.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Skuray
 * @Date 2023/8/21 10:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataParam {

    /**
     * 查看用户的链路信息
     */
    private String receiver;

    /**
     * 业务id(用于追踪数据)  <p>
     * 生成逻辑：TaskInfoUtils  <p>
     * 如果传入的是模板id，则生成当天的业务id
     */
    private String businessId;

    /**
     * 日期时间(检索短信的条件使用)
     */
    private Long dateTime;
}
