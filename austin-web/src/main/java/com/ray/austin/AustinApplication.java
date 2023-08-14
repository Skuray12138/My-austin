package com.ray.austin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AustinApplication {
    public static void main(String[] args) {

        /**
         * 如果需要启动动态配置
         * 1. 启动apollo
         * 2. 将application.properties 配置文件的 austin.apollo.enable 改成true
         * 3. 将下方的property替换为真实的ip和port
         */
        System.setProperty("apollo.config-service", "http://austin-apollo-config:8080");

        SpringApplication.run(AustinApplication.class,args);
    }
}
