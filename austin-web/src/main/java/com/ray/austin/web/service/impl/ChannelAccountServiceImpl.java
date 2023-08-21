package com.ray.austin.web.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ray.austin.common.enums.constant.CommonConstant;
import com.ray.austin.common.enums.constant.PushConstant;
import com.ray.austin.dao.ChannelAccountDao;
import com.ray.austin.domain.ChannelAccount;
import com.ray.austin.web.service.ChannelAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/20 16:41
 */
@Service
public class ChannelAccountServiceImpl implements ChannelAccountService {

    @Autowired
    private ChannelAccountDao channelAccountDao;

    /**
     * 保存/修改 渠道账号信息
     * @param channelAccount
     * @return
     */
    @Override
    public ChannelAccount save(ChannelAccount channelAccount) {
        // 若不存在，则创建
        if (Objects.isNull(channelAccount.getId())){
            channelAccount.setCreated(Math.toIntExact(DateUtil.currentSeconds())); // 创建时间
            channelAccount.setUpdated(CommonConstant.FALSE);
        }
        // 若存在，则更新
        channelAccount.setCreator(StrUtil.isBlank(channelAccount.getCreator()) ?
                PushConstant.DEFAULT_CREATOR : channelAccount.getCreator());

        channelAccount.setUpdated(Math.toIntExact(DateUtil.currentSeconds()));
        return channelAccountDao.save(channelAccount);

    }

    /**
     * 根据渠道标识 查询账号信息
     * @param channelType
     * @param creator
     * @return
     */
    @Override
    public List<ChannelAccount> queryByChannelType(Integer channelType, String creator) {

        return channelAccountDao.findAllByIsDeletedEqualsAndCreatedEqualsAndSendChannelEquals(CommonConstant.FALSE,
                creator, channelType);
    }

    /**
     * 列表信息
     * @param creator
     * @return
     */
    @Override
    public List<ChannelAccount> list(String creator) {
        return channelAccountDao.findAllByCreatorEquals(creator);
    }

    /**
     * 软删除
     * @param ids
     */
    @Override
    public void deleteById(List<Long> ids) {
        channelAccountDao.deleteAllById(ids);
    }
}
