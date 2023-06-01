package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @ClassDescription: redisTemplate配置类
 * 配置工厂
 * 序列化处理（解决redis客户端工具显示序列化导致的二进制）
 * 创建对象（解决了redisTemplate为null的问题）
 * @Author：白永祥
 * @Date：2023/6/1
 */
@Configuration
public class RedisConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
        System.out.println("redis序列化-->");
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        //连接工厂
        redisTemplate.setConnectionFactory(factory);
        //键序列化
        redisTemplate.setKeySerializer(redisSerializer);
        //值序列化
        redisTemplate.setValueSerializer(redisSerializer);
        //key hashMap序列化
        redisTemplate.setHashKeySerializer(redisSerializer);
        //value hashMap序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }
}
