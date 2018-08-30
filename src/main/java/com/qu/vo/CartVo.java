package com.qu.vo;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2018/6/22 0022.
 */

public class CartVo {
    List<ShopCartVo> shopCartVoList;
    Double totalprice;

    public List<ShopCartVo> getShopCartVoList() {
        return shopCartVoList;
    }

    public void setShopCartVoList(List<ShopCartVo> shopCartVoList) {
        this.shopCartVoList = shopCartVoList;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }
}