package com.ray.austin.web.controller;

import cn.hutool.core.util.StrUtil;
import com.ray.austin.common.enums.constant.PushConstant;
import com.ray.austin.dao.ChannelAccountDao;
import com.ray.austin.domain.ChannelAccount;
import com.ray.austin.web.amis.CommonAmisVo;
import com.ray.austin.web.annotation.AustinAspect;
import com.ray.austin.web.annotation.AustinResult;
import com.ray.austin.web.service.ChannelAccountService;
import com.ray.austin.web.utils.Convert4Amis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Skuray
 * @Date 2023/8/20 15:59
 * 渠道账号管理接口
 */
@Slf4j
@AustinAspect
@AustinResult
@RestController
@RequestMapping("/account")
@Api("渠道账号管理接口")
public class ChannelAccountController {

    @Autowired
    private ChannelAccountDao channelAccountDao;

    @Autowired
    private ChannelAccountService channelAccountService;

    /**
     * 如果id存在则修改
     * 如果id不存在则保存
     * @param channelAccount
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("/保存数据")
    public ChannelAccount saveOrUpdate(@RequestBody ChannelAccount channelAccount){
        channelAccount.setCreator(StrUtil.isBlank(channelAccount.getCreator()) ?
                PushConstant.DEFAULT_CREATOR : channelAccount.getCreator());
        return channelAccountService.save(channelAccount);
    }

    /**
     * 根据渠道标识查询账号相关的信息
     * @param channelType
     * @param creator
     * @return
     */
    @GetMapping("/queryByChannelType")
    @ApiOperation("/根据渠道标识查询账号相关记录")
    public List<CommonAmisVo> query(Integer channelType, String creator){
        creator = StrUtil.isBlank(creator) ? PushConstant.DEFAULT_CREATOR : creator;
        List<ChannelAccount> channelAccounts = channelAccountService.queryByChannelType(channelType, creator);
        return Convert4Amis.getChannelAccountVo(channelAccounts, channelType);

    }

    /**
     * 所有的渠道账号信息
     * @param creator
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("/渠道账号列表信息")
    public List<ChannelAccount> list(String creator){
        creator = StrUtil.isBlank(creator) ? PushConstant.DEFAULT_CREATOR : creator;
        return channelAccountService.list(creator);
    }

    /**
     * 根据id删除
     * id使用 , 分隔
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("/根据Ids删除")
    public void deleteByIds(@PathVariable("id")String id){
        if (StrUtil.isNotBlank(id)){
            List<Long> idList = Arrays.stream(id.split(StrUtil.COMMA))
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            channelAccountService.deleteById(idList);
        }
    }
}
