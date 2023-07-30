package com.ray.austin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author Skuray
 * @Date 2023/7/29 17:02
 * 消息模板: 模板标题、发送渠道、消息类型、审核状态、发送id、工单id等
 * @Entity : 定义的对象会成为被JPA管理的实体类，并有相应的数据库表与该实体类存在映射
 *           表示该类是实体类，当要从数据库读取数据时，数据库中被查询的表在程序中要有相对应的映射实体类。当查询数据库该表时，后台自动实例化该
 *           映射的实体类，然后将查询到的数据填充到实例化的对象中。
 *           必须要有@Id表明哪个属性是主键；
 *           通过在属性上添加注解@Column(name = "book_name")表明该属性是表内的book_name字段(非必要)
 *           注解@Transient表示该属性不在数据库内，数据库表内没有相应的字段，填值时忽略该属性
 *           注解GeneratedValue注解表示注解自动生成，strategy则表示主键的生成策略
 */
@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MessageTemplate implements Serializable {

    /**
     * 唯一标识主键，由数据库生成，自增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 模板标题
     */
    private String name;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**+
     * 工单ID(模板审核走工单)
     */
    private String flowId;

    /**
     * 消息状态
     */
    private Integer msgStatus;

    /**
     * 定时任务Id,由xxl-job返回
     */
    private Integer cronTaskId;

    /**
     * 定时发送的人群的文件路径
     */
    private String cronCrowdPath;

    /**
     * 发送的Id类型
     */
    private Integer idType;

    /**
     * 发送渠道
     */
    private Integer sendChannel;

    /**
     * 模板类型
     */
    private Integer templateType;

    /**
     * 屏蔽类型
     */
    private Integer shieldType;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 推送消息的时间
     * 0: 立即发送
     * else: crontab表达式
     */
    private String expectPushTime;

    /**
     * 消息内容 {$var}为占位符
     */
    private String msgContent;

    /**
     * 发送账号
     * 邮件下有多个发送账号、短信可有多个发送账号
     */
    private Integer sendAccount;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String updator;

    /**
     * 审核者
     */
    private String auditor;

    /**
     * 业务方团队
     */
    private String team;

    /**
     * 业务方
     */
    private String proposer;

    /**
     * 是否删除
     * 0: 未删除
     * 1: 已删除
     */
    private Integer isDeleted;

    /**
     * 创建时间 单位s
     */
    private Integer created;

    /**
     * 更新时间 单位s
     */
    private Integer updated;
}
