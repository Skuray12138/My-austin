package com.ray.austin.web.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Skuray
 * @Date 2023/8/19 20:54
 * 切面类
 */
@Slf4j
@Aspect
@Component
public class AustinAspect {

    @Autowired
    private HttpServletRequest request;

    /**
     * 同一个请求的key
     */
    private final String REQUEST_ID_KEY = "request_unique_id";

    /**
     * 只切AustinAspect注解
     */
    @Pointcut("@within(com.ray.austin.web.annotation.AustinAspect) || @annotation(com.ray.austin.web.annotation.AustinAspect)")
    public void executeService(){
    }

    /**
     * 前置通知，方法调用前被执行
     * @param joinPoint
     */
    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 打印请求日志
        this.printRequestLog(methodSignature, joinPoint.getArgs());
    }

    /**
     * 异常通知
     * @param ex
     */
    @AfterThrowing(value = "executeService()", throwing = "ex")
    public void doAfterThrowingAdvice(Throwable ex){
        printExceptionLog(ex);
    }

    /**
     * 打印异常日志
     * @param ex
     */
    private void printExceptionLog(Throwable ex) {
        JSONObject logVo = new JSONObject();
        logVo.put("id", request.getAttribute(REQUEST_ID_KEY));
        log.error(JSON.toJSONString(logVo), ex);
    }

    /**
     * 打印请求日志
     * @param methodSignature
     * @param args
     */
    private void printRequestLog(MethodSignature methodSignature, Object[] args) {
    }


}
