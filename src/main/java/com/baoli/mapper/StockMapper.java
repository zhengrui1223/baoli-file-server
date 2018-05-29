package com.baoli.mapper;

import com.baoli.mapper.base.BaseMapper;
import com.baoli.model.StockEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/************************************************************
 * @Description:
 * @Author: zhengrui
 * @Date 2018-05-29 14:00
 ************************************************************/

@Repository
public interface StockMapper extends BaseMapper<StockEntity, Integer> {
    StockEntity getByGoodsName(@Param("goodsName") String goodsName);

    Integer updateGoodsCount(StockEntity entity);
}
