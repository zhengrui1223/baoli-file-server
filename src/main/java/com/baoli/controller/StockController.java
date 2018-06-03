package com.baoli.controller;

import com.baoli.component.redis.RedisCacheManager;
import com.baoli.service.StockService;
import com.baoli.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 17:19
 ************************************************************/

@RestController
@RequestMapping(value = "/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @ResponseBody
    @RequestMapping(value = "/secKillByDB1")
    public Boolean secKillWay1(@RequestParam String goodsName, @RequestParam Integer buyCount) {
        return stockService.secKillByDBWay1(goodsName, buyCount);
    }

    @ResponseBody
    @RequestMapping(value = "/initStock")
    public Object initStock() {
        redisCacheManager.set(Context.GOODS_NAME, "20");
        return redisCacheManager.get(Context.GOODS_NAME);
    }

    @ResponseBody
    @RequestMapping(value = "/getStock")
    public Object getStock() {
        return redisCacheManager.get(Context.GOODS_NAME);
    }

    @ResponseBody
    @RequestMapping(value = "/secKillByRedis")
    public Boolean secKillByRedis(@RequestParam String goodsName, @RequestParam Integer buyCount) {
        return stockService.secKillByRedis(goodsName, buyCount);
    }
}
