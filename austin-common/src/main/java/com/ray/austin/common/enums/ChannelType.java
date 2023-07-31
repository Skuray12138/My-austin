package com.ray.austin.common.enums;

import com.ray.austin.common.dto.model.EmailContentModel;
import com.ray.austin.common.dto.model.ImContentModel;
import com.ray.austin.common.dto.model.PushContentModel;
import com.ray.austin.common.dto.model.SmsContentModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/7/31 16:41
 * 发送渠道类型枚举
 * @Attribute 编码值code、描述、内容模型Class(指向对应的XxxContentModel)、英文标识codeEn
 * @Method getChanelModelClassByCode通过code获取class；getEnumByCode通过code获取enum
 */
@Getter
@ToString
@AllArgsConstructor
public enum ChannelType {

    IM(10, "IM(站内信)", ImContentModel.class, "im"),
    PUSH(20, "push(通知栏)", PushContentModel.class, "push"),
    SMS(30, "sms(短信)", SmsContentModel.class, "sms"),
    EMAIL(40, "email(邮件)", EmailContentModel.class, "email");

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    /**
     * 内容模板Class
     */
    private Class contentModelClass;

    /**
     * 英文标识
     */
    private String codeEn;

    /**
     * 通过code获取Class
     * @param code
     * @return
     */
    public static Class getChanelModelClassByCode(Integer code){
        ChannelType[] values = values();

        for (ChannelType value : values) {
            if (value.getCode().equals(code)){
                return value.getContentModelClass();
            }
        }
        return null;
    }

    /**
     * 通过code获取enum
     * @param code
     * @return
     */
    public static ChannelType getEnumByCode(Integer code){
        ChannelType[] values = values();

        for (ChannelType value : values) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
