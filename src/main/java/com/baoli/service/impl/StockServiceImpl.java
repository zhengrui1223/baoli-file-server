package com.baoli.service.impl;

import com.baoli.mapper.StockMapper;
import com.baoli.model.StockEntity;
import com.baoli.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 13:59
 ************************************************************/

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public Integer update(StockEntity entity) {
        return stockMapper.update(entity);
    }

    @Override
    public StockEntity getByGoodsName(String goodsName) {
        return stockMapper.getByGoodsName(goodsName);
    }

    @Override
    public Boolean secKillWay1(String goodsName, Integer buyCount) {
        StockEntity stock = getByGoodsName(goodsName);
        if (stock.getGoodsCount() - buyCount < 0) {
            return false;
        }
        stock.setGoodsCount(buyCount);

        Integer success = stockMapper.updateGoodsCount(stock);
        if (success > 0) {
            return true;
        }

        waitForLock();

        return secKillWay1(goodsName, buyCount);
    }

    private void waitForLock() {
        try {
            Thread.sleep(new Random().nextInt(10) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
