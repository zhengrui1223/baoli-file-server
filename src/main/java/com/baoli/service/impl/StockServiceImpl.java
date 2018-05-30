package com.baoli.service.impl;

import com.baoli.mapper.StockMapper;
import com.baoli.model.StockEntity;
import com.baoli.service.StockService;
import com.baoli.util.Context;
import com.baoli.util.LogExceptionStackTrace;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 13:59
 ************************************************************/

@Service
public class StockServiceImpl implements StockService {
    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Integer update(StockEntity entity) {
        return stockMapper.update(entity);
    }

    @Override
    public StockEntity getByGoodsName(String goodsName) {
        return stockMapper.getByGoodsName(goodsName);
    }

    @Override
    public Boolean secKillByDBWay1(String goodsName, Integer buyCount) {
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

        return secKillByDBWay1(goodsName, buyCount);
    }

    @Override
    public Boolean secKillByRedis(String goodsName, final Integer buyCount) {

        //监听商品数量有无变化
        redisTemplate.watch(Context.GOODS_NAME);

        Object cacheObj = redisTemplate.opsForValue().get(Context.GOODS_NAME);
        Integer leftAmount = 0;
        if (cacheObj != null) {
            leftAmount = Integer.valueOf(String.valueOf(cacheObj));
        }

        if (leftAmount - buyCount < 0) {
            return false;
        }

        //开启事物管理
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.multi();

        redisTemplate.opsForValue().increment(Context.GOODS_NAME, -buyCount);

        //提交事物
        List<Object> exec = redisTemplate.exec();

        if (CollectionUtils.isNotEmpty(exec)) {
            return true;
        }

        waitForLock();

        return secKillByRedis(goodsName, buyCount);
    }

    private void waitForLock() {
        try {
            Thread.sleep(new Random().nextInt(10) + 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
