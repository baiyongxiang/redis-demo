package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.swing.plaf.IconUIResource;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private JedisPool jedisPool;


    @RequestMapping("/one")
    @ResponseBody
    public String demeOne(){
        redisTemplate.opsForHash().put("xiobai","wqeqwe","qeqw");
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("update=demo", "update demo set name='xiaobai1' where id =100", 30, TimeUnit.SECONDS);
        return "success";
    }
    @RequestMapping("/two")
    @ResponseBody
    public String DemoTwo(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        boolean b;
        for (;;){
           if (Boolean.TRUE.equals(redisTemplate.hasKey("update=demo"))){
               continue;
           }else {
             b= Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent("update=demo", "update demo set name='xiaobai1' where id =100", 30, TimeUnit.MILLISECONDS));
            break;
           }
        }
        stopWatch.stop();
        System.out.println("获取锁时间为："+stopWatch.getTotalTimeSeconds());
        System.out.println(b);
        return "success";
    }


    @RequestMapping("/three")
    @ResponseBody
    public String demeThree(){
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("update=demo", "update demo set name='xiaobai1' where id =100", 20, TimeUnit.SECONDS);
        try {
            Thread.sleep(10000);
            System.out.println("业务处理时间为："+5);
            redisTemplate.delete("update=demo");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }
    @RequestMapping("/four")
    @ResponseBody
    public String demeFour(){
        Jedis jedis = jedisPool.getResource();
        jedis.setnx("key-demo","test");
        jedis.get("key-demo");
        jedis.expire("key-demo",30);

        System.out.println("----------------------");
        Boolean exists = jedis.exists("key-demo");
        System.out.println(exists);
        try {
            Thread.sleep(310000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Boolean exists1 = jedis.exists("key-demo");
        System.out.println("ex"+exists1);
        return "success";
    }
}
