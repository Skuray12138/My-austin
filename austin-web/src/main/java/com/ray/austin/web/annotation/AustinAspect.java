package com.ray.austin.web.annotation;

import java.lang.annotation.*;

/**
 * @Author Skuray
 * @Date 2023/8/19 17:41
 * 接口切面注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AustinAspect {
}
