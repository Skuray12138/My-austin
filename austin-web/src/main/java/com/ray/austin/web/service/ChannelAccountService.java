package com.ray.austin.web.service;

import com.ray.austin.domain.ChannelAccount;

import java.util.List;

/**
 * @Author Skuray
 * @Date 2023/8/20 16:27
 * 渠道账号接口
 */

public interface ChannelAccountService {

    /**
     * 保存/修改 渠道账号信息
     * @param channelAccount
     * @return
     */
    ChannelAccount save(ChannelAccount channelAccount);

    /**
     * 根据渠道标识 查询账号信息
     * @param channelType
     * @param creator
     * @return
     */
    List<ChannelAccount> queryByChannelType(Integer channelType, String creator);

    /**
     * 列表信息
     * @param creator
     * @return
     */
    List<ChannelAccount> list(String creator);

    /**
     * 软删除(令属性deleted = 1)
     * @param ids
     */
    void deleteById(List<Long> ids);
}
