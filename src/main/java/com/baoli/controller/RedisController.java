package com.baoli.controller;

import com.baoli.component.redis.RedisCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 21:09
 ************************************************************/

@RestController
@RequestMapping(value = "/redis")
public class RedisController {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @ResponseBody
    @RequestMapping("/get")
    public Object get(@RequestParam("key") String key) {
        return redisCacheManager.get(key);
    }
}
