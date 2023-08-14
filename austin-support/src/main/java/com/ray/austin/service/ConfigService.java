package com.ray.austin.service;

/**
 * @Author Skuray
 * @Date 2023/8/12 21:40
 * 读取配置服务
 */

public interface ConfigService {

    /**
     * 读取配置
     * 1. 当启动使用了apollo或nacos，有限读取远程配置
     * 2. 当没有启动远程配置，读取本地 local.properties配置文件的内容
     * @param key
     * @param defaultValue
     * @return
     */
    String getProperty(String key, String defaultValue);
}
