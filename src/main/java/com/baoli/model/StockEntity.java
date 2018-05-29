package com.baoli.model;

/************************************************************
 * @Description: 商品库存信息
 * @Author: zhengrui
 * @Date 2018-05-29 13:58
 ************************************************************/

public class StockEntity {
    private Integer id;

    private String goodsName;

    private Integer goodsCount;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
