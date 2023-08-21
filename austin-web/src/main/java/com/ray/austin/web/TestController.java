package com.ray.austin.web;

import com.alibaba.fastjson.JSON;
import com.ray.austin.dao.MessageTemplateDao;
import com.ray.austin.domain.MessageTemplate;
import com.ray.austin.service.api.domain.MessageParam;
import com.ray.austin.service.api.domain.SendRequest;
import com.ray.austin.service.api.domain.SendResponse;
import com.ray.austin.service.api.enmus.BusinessCode;
import com.ray.austin.service.api.service.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private final MessageTemplateDao messageTemplateDao;
    @Resource
    private SendService sendService;

    public TestController(MessageTemplateDao messageTemplateDao){
        this.messageTemplateDao = messageTemplateDao;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String testRedis(){
        redisTemplate.opsForValue().set("hel","ddd");
        return redisTemplate.opsForValue().get("hel");
    }

    @RequestMapping("/send")
    private String testSend(){
        SendRequest sendRequest = SendRequest.builder()
                .code(BusinessCode.COMMON_SEND.getCode())
                .messageTemplateId(1L)
                .messageParam(MessageParam.builder().receiver("13279177317").build())
                .build();
        SendResponse response = sendService.send(sendRequest);
        return JSON.toJSONString(response);
    }

    @RequestMapping("/test")
    private String test(){
        System.out.println("test!");
        return "hello";
    }

    @RequestMapping("/database")
    private String testDataBase(){
        List<MessageTemplate> list = messageTemplateDao.findAllByIsDeletedEqualsOrderByUpdatedDesc(0, PageRequest.of(0,10));
        return JSON.toJSONString(list);
    }
}
