package com.ray.austin.utils;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.shaded.com.google.common.base.Throwables;
import com.ray.austin.common.constant.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author Skuray
 * @Date 2023/8/7 21:06
 * 对Redis操作进行二次封装
 */
@Component
@Slf4j
public class RedisUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将结果封装为Map
     * @param keys
     * @return
     * 将Redis中存放的一系列keys,取出对应的values,然后统一放到一个Map集合中
     */
    public Map<String, String> mGet(List<String> keys){
        HashMap<String, String> result = new HashMap<>(keys.size());

        try {
            // 批量获取值
            List<String> value = redisTemplate.opsForValue().multiGet(keys);
            if (CollUtil.isNotEmpty(value)){
                for (int i = 0; i < keys.size(); i++) {
                    result.put(keys.get(i), value.get(i));
                }
            }
        } catch (Exception e) {
            log.error("RedisUtils#mGet fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return result;

    }

    /**
     * 通过键名，获取hash结构中 该键名的所有key-value对
     * @param key
     * @return
     */
    public Map<Object, Object> hGetAll(String key){
        try {
            // 通过键名key获得对应变量中的键值对.键名相当于类名,键值相当于属性
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
            return entries;
        } catch (Exception e) {
            log.error("RedisUtils#hGetAll fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 获取列表指定范围内的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lRange(String key, long start, long end){
        try {
            // 获取列表指定范围内的元素
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e){
            log.error("RedisUtils#lRange fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 设置 key-value 并设置过期时间
     * @param keyValues
     * @param seconds
     */
    public void pipelineSetEx(Map<String, String> keyValues, Long seconds){
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection ->{
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    connection.setEx(entry.getKey().getBytes(), seconds, entry.getValue().getBytes());
                }
                return null;
            } );
        } catch (Exception e){
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * lpush方法，并指定过期时间
     * @param key
     * @param value
     * @param seconds
     */
    public void lPush(String key, String value, Long seconds){
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection ->{
                // 存储在list头部，添加时放在最前面索引处
                connection.lPush(key.getBytes(), value.getBytes());
                // 设置过期时间(密钥，日期)
                connection.expire(key.getBytes(), seconds);
                return null;
            });
        } catch (Exception e){
            log.error("RedisUtils#lPush fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * lLen方法 <p>
     * 获取当前key的List列表长度
     * @param key
     * @return
     */
    public Long lLen(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e){
            log.error("RedisUtils#lLen fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return 0L;
    }

    /**
     * lPop方法 <p>
     * 移出并获取列表中第一个元素
     * @param key
     * @return
     */
    public String lPop(String key){
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e){
            log.error("RedisUtils#pipelineSetEx fail! e:{}", Throwables.getStackTraceAsString(e));
        }
        return "";
    }

    /**
     * 设置 key-value 并设置过期时间
     * @param keyValues
     * @param seconds 过期时间
     * @param delta 自增的步长
     */
    public void pipelineHashIncrByEx(Map<String, String> keyValues, Long seconds, Long delta){
        try {
            redisTemplate.executePipelined((RedisCallback<String>) connection -> {
                for (Map.Entry<String, String> entry : keyValues.entrySet()) {
                    connection.hIncrBy(entry.getKey().getBytes(), entry.getValue().getBytes(), delta);
                    connection.expire(entry.getKey().getBytes(), seconds);
                }
                return null;
            });

        } catch (Exception e){
            log.error("redis pipelineSetEX fail! e:{}", Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 执行Lua脚本并返回执行结果
     * --KEY[1]: 限流key
     * -- ARGV[1]: 限流窗口
     * -- ARGV[2]: 当前时间戳(作为score)
     * -- ARGV[3]: 阈值
     * -- ARGV[4]: score对应的唯一value
     * @param redisScript
     * @param keys
     * @param args
     * @return
     */
    public Boolean execLimitLua(RedisScript<Long> redisScript, List<String> keys, String... args){
        try {
            // 超过阈值返回1，未超过阈值返回0
            Long execute = redisTemplate.execute(redisScript, keys, args);
            if (Objects.isNull(execute)){
                return false;
            }
            // 超过阈值
            return CommonConstant.TRUE.equals(execute.intValue());

        } catch (Exception e){
            log.error("redis execLimitLua fail! e:{}", Throwables.getStackTraceAsString(e));
        }

        return false;
    }
}
