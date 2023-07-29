package com.ray.austin.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test")
    private String test(){
        System.out.println("test!");
        return "hello";
    }
}
