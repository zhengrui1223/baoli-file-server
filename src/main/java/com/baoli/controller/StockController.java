package com.baoli.controller;

import com.baoli.service.StockService;
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

    @ResponseBody
    @RequestMapping(value = "/secKillWay1")
    public Boolean secKillWay1(@RequestParam String goodsName, @RequestParam Integer buyCount) {
        return  stockService.secKillWay1(goodsName, buyCount);
    }
}
