package com.qu.service;

import com.qu.bean.ShopCart;
import com.qu.vo.CartVo;

/**
 * Created by Administrator on 2018/6/22 0022.
 */
public interface IShopcartService {
    public String addshopcart(ShopCart sc);
    public int updatecartnumber(ShopCart sc);
    public CartVo getCartVo(int UserId);
    public int deleteByIds(String[]productIds,int userid);
    public int setCheckedstatus(String kind,Integer userid,Integer productid);
}