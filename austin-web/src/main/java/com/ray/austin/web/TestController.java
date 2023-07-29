package com.ray.austin.web;

import com.alibaba.fastjson.JSON;
import com.ray.austin.dao.MessageTemplateDao;
import com.ray.austin.domain.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class TestController {

    private final MessageTemplateDao messageTemplateDao;

    public TestController(MessageTemplateDao messageTemplateDao){
        this.messageTemplateDao = messageTemplateDao;
    }

    @RequestMapping("/test")
    private String test(){
        System.out.println("test!");
        return "hello";
    }

    @RequestMapping("/database")
    private String testDataBase(){
        List<MessageTemplate> list = messageTemplateDao.findAllByIsDeletedEquals(0, PageRequest.of(0,10));
        return JSON.toJSONString(list);
    }
}
