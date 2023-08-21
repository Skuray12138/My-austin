package com.ray.austin.common.vo;

import com.ray.austin.common.enums.RespStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author Skuray
 * @Date 2023/7/31 20:28
 * 成功、失败时返回的信息
 * @Attribute 响应状态、响应编码、返回数据
 */

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class BasicResultVO<T> {

    /**
     * 响应状态
     */
    private String status;

    /**
     * 响应编码
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    public BasicResultVO(RespStatusEnum status) {
        this(status, null);
    }

    public BasicResultVO(RespStatusEnum status, T data) {
        this(status, status.getMsg(), data);
    }

    public BasicResultVO(RespStatusEnum status, String msg, T data) {
        this.status = status.getCode();
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认响应成功
     *
     * @return
     */
    public static BasicResultVO<Void> success() {
        return new BasicResultVO<>(RespStatusEnum.SUCCESS);
    }

    /**
     * 自定义信息的成功响应
     * <p>
     * 通常用作插入成功等并显示具体操作通知如: return BasicResultVO.success("发送信息成功")
     *
     * @param msg 信息
     * @param <T>
     * @return
     */
    public static <T> BasicResultVO<T> success(String msg) {
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, msg, null);
    }

    /**
     * 带数据的成功响应
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BasicResultVO<T> success(T data) {
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, data);
    }

    /**
     * 默认失败响应
     * @return
     * @param <T>
     */
    public static <T> BasicResultVO<T> fail() {
        return new BasicResultVO<>(RespStatusEnum.FAIL, RespStatusEnum.FAIL.getMsg(), null);
    }

    /**
     * 自定义错误信息的失败响应
     * @param msg 错误信息
     * @return
     * @param <T>
     */
    public static <T> BasicResultVO<T> fail(String msg) {
        return fail(RespStatusEnum.FAIL, msg);
    }

    /**
     * 自定义状态的失败响应
     * @param status
     * @return
     * @param <T>
     */
    public static <T> BasicResultVO<T> fail(RespStatusEnum status) {
        return fail(status, status.getMsg());
    }

    /**
     * 自定义状态和信息的失败响应
     * @param status 状态
     * @param msg 响应
     * @return
     * @param <T>
     */
    public static <T> BasicResultVO<T> fail(RespStatusEnum status, String msg) {
        return new BasicResultVO<>(status, msg, null);
    }
}
