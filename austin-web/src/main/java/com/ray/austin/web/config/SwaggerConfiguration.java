package com.ray.austin.web.config;

import io.swagger.annotations.ApiModel;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Skuray
 * @Date 2023/8/20 15:44
 * swagger配置类
 */
@Component
@EnableOpenApi
@ApiModel
public class SwaggerConfiguration {

    public Docket webApiDoc(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("用户端接口文档")
                .pathMapping("/")
                .enable(true) //定义是否开启Swagger
                .apiInfo(apiInfo()) //配置文档的元信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ray.austin.web.controller"))
                //正则匹配请求路径，并分配到当前项目组
                //.paths(PathSelectors.ant("/api/**"))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Austin平台")
                .description("消息推送接口文档")
                .contact(new Contact("ray", "https://github.com/ray/Austin", "1003369785@qq.com"))
                .version("v1.0")
                .build();
    }
}
