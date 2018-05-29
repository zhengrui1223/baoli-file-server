package com.baoli.service;

import com.baoli.model.StockEntity;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 13:59
 ************************************************************/

public interface StockService {
    Integer update(StockEntity entity);

    StockEntity getByGoodsName(String goodsName);

    Boolean secKillWay1(String goodsName, Integer buyCount);
}
