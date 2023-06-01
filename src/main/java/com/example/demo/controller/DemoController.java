package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;


    @RequestMapping("/one")
    @ResponseBody
    public String demo(){
        redisTemplate.opsForHash().put("xiobai","wqeqwe","qeqw");
        System.out.println(redisTemplate.opsForHash().get("xiobai","wqeqwe"));
        return "success";
    }
}
