package com.ray.austin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/8/5 23:27
 * 发送消息的类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageType {

    NOTICE(10,"通知类消息","notice"),
    MARKETING(20,"营销类短信","marketing"),
    AUTH_CODE(30,"验证码短信","auth_code");

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    /**
     * 英文标识
     */
    private String codeEn;

    /**
     * 通过code获取对应enum
     * @param code
     * @return
     */
    public static MessageType getEnumByCode(Integer code){
        MessageType[] values = values();
        for (MessageType value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

}
