package com.ray.austin.web.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.ray.austin.common.enums.RespStatusEnum;
import com.ray.austin.common.vo.BasicResultVO;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.service.api.domain.MessageParam;
import com.ray.austin.service.api.domain.SendRequest;
import com.ray.austin.service.api.domain.SendResponse;
import com.ray.austin.service.api.enmus.BusinessCode;
import com.ray.austin.service.api.service.SendService;
import com.ray.austin.web.amis.CommonAmisVo;
import com.ray.austin.web.exception.CommonException;
import com.ray.austin.web.service.MessageTemplateService;
import com.ray.austin.web.utils.Convert4Amis;
import com.ray.austin.web.vo.MessageTemplateParam;
import com.ray.austin.web.vo.MessageTemplateVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Skuray
 * @Date 2023/8/21 11:11
 * 消息模板管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/messageTemplate")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private SendService sendService;

    @Value("${austin.business.upload.crowd.path}") //附件下载地址
    private String dataPath;

    /**
     * 若id不存在,则保存
     * 若id存在,则修改
     * @param messageTemplate
     * @return
     */
    @PostMapping("/save")
    public MessageTemplate saveOrUpdate(@RequestBody MessageTemplate messageTemplate){
        return messageTemplateService.saveOrUpdate(messageTemplate);
    }
    /**
     * 列表数据
     * @param messageTemplateParam
     * @return
     */
    @GetMapping("/list")
    public MessageTemplateVo queryList(@Validated MessageTemplateParam messageTemplateParam){ //@Validated 参数校验
        Page<MessageTemplate> messageTemplates = messageTemplateService.queryList(messageTemplateParam);
        List<Map<String, Object>> result = Convert4Amis.flatListMap(messageTemplates.toList());
        return MessageTemplateVo.builder()
                .count(messageTemplates.getTotalElements()).rows(result).build();
    }
    /**
     * 根据id查找
     * @param id
     * @return
     */
    @GetMapping("query/{id}")
    public Map<String, Object> queryById(@PathVariable("id") Long id){
        return Convert4Amis.flatSingleMap(messageTemplateService.queryById(id));
    }

    /**
     * 根据Id复制
     * @param id
     */
    @PostMapping("copy/{id}")
    public void copyById(@PathVariable("id") Long id) {
        messageTemplateService.copy(id);
    }

    /**
     * 根据id删除
     * @param id
     */
    @DeleteMapping("delete/{id}")
    public void deleteByIds(@PathVariable("id") String id) {
        if (StrUtil.isNotBlank(id)) {
            //id使用多个 逗号 分隔开来
            List<Long> idList = Arrays.stream(id.split(StrUtil.COMMA)).map(Long::valueOf).collect(Collectors.toList());
            messageTemplateService.deleteByIds(idList);
        }
    }

    /**
     * 测试发送接口
     */
    @PostMapping("test")
    public SendResponse test(@RequestBody MessageTemplateParam messageTemplateParam){
        // 消息参数中的参数信息
        Map<String, String> variables = JSON.parseObject(messageTemplateParam.getMsgContent(), Map.class);
        // 填充消息参数
        MessageParam messageParam = MessageParam.builder()
                .receiver(messageTemplateParam.getReceiver())
                .variables(variables).build();
        // 填充发送请求参数
        SendRequest sendRequest = SendRequest.builder()
                .code(BusinessCode.COMMON_SEND.getCode())
                .messageTemplateId(messageTemplateParam.getId())
                .messageParam(messageParam).build();
        // 请求返回的响应
        SendResponse response = sendService.send(sendRequest);

        // 若发送不成功，抛出响应消息异常
        if (!Objects.equals(response.getCode(), RespStatusEnum.SUCCESS.getCode())){
            throw new CommonException(response.getMsg());
        }
        return response;

    }


    /**
     * 获取需要测试的模板占位符,透出给Amis
     * @param id
     * @return
     */
    @PostMapping("test/content")
    public CommonAmisVo test(Long id) {
        MessageTemplate messageTemplate = messageTemplateService.queryById(id);

        return Convert4Amis.getTestContent(messageTemplate.getMsgContent());
    }

    /**
     * 启动模块的定时任务
     * @param id
     * @return
     */
    @PostMapping("/start/{id}")
    @ApiOperation("/启动模块的定时任务")
    public BasicResultVO start(@RequestBody @PathVariable("id") Long id){
        return messageTemplateService.startCronTask(id);
    }

    /**
     * 暂停模板的定时任务
     * @param id
     * @return
     */
    @PostMapping("stop/{id}")
    @ApiOperation("/暂停模板的定时任务")
    public BasicResultVO stop(@RequestBody @PathVariable("id") Long id){
        return messageTemplateService.stopCronTask(id);
    }

    public HashMap<Object, Object> upload(@RequestParam("file") MultipartFile file){
        // 文件保存路径
        String filePath = dataPath + IdUtil.fastSimpleUUID() + file.getOriginalFilename();
        try {
            File localFile = new File(filePath);
            if (!localFile.exists()){
                localFile.mkdirs();
            }
            file.transferTo(localFile);
        } catch (Exception e) {
            log.error("MessageTemplateController#upload fail! e:{}, params:{}", Throwables.getStackTraceAsString(e), JSON.toJSONString(file));
            throw new CommonException(RespStatusEnum.SERVICE_ERROR);
        }
        return MapUtil.of(new String[][]{{"value", filePath}});
    }
}
