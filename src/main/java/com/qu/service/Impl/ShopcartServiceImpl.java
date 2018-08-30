package com.qu.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qu.bean.Goods;
import com.qu.bean.ShopCart;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.dao.GoodsMapper;
import com.qu.dao.ShopCartMapper;
import com.qu.service.IShopcartService;
import com.qu.util.BigdecimalUtil;
import com.qu.vo.CartVo;
import com.qu.vo.ShopCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22 0022.
 */
@Service
public class ShopcartServiceImpl implements IShopcartService {
    @Autowired
    ShopCartMapper shopCartMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public String addshopcart(ShopCart sc) {
        ShopCart scart = shopCartMapper.selectbyuseridpid(sc.getUserId().intValue(), sc.getProductId().intValue());
        if(null==scart){
            sc.setChecked(Const.CK.Check);
        int number = shopCartMapper.insert(sc);
        if(number>0){
            return JSONObject.toJSONString(Result.getsuccessful("add ok",this.getCartVo(sc.getUserId())));
         }
        return JSONObject.toJSONString(Result.getfailed("add fail"));
        }
         int count = scart.getQuantity()+sc.getQuantity();
        sc.setQuantity(count);
        int number2 = shopCartMapper.updateshopcartnumber(sc);
        if(number2>0){
            return JSONObject.toJSONString(Result.getsuccessful("update ok",this.getCartVo(sc.getUserId())));
        }
        return JSONObject.toJSONString(Result.getfailed("update fail"));
}

    public int updatecartnumber(ShopCart sc){
         int number = shopCartMapper.updateshopcartnumber(sc);
         return  number;
    }

    public CartVo getCartVo(int UserId){
        Double TotalPrice=0.0;
        List<ShopCartVo> shopcartvo = Lists.newArrayList();
        CartVo cartVo = new CartVo();
        List<ShopCart> cartList = shopCartMapper.selecbyuserid(UserId);
        for(ShopCart sc:cartList){
            ShopCartVo scvo = new ShopCartVo();
            scvo.setId(sc.getId());
            scvo.setUserId(sc.getUserId());
            scvo.setProductId(sc.getProductId());
            scvo.setQuantity(sc.getQuantity());
            scvo.setChecked(sc.getChecked());
            Goods goods = goodsMapper.selectByPrimaryKey(sc.getProductId());
            if(null!=goods){
                scvo.setPrice(goods.getPrice().doubleValue());
                scvo.setProductdetail(goods.getDetail());
                scvo.setProductname(goods.getName());
                scvo.setProductsubtitle(goods.getSubtitle());
                scvo.setStock(goods.getStock());
            }
            System.out.println("Quantity"+sc.getQuantity().intValue());
            System.out.println("Stock"+goods.getStock().intValue());
            if(sc.getQuantity().intValue()>goods.getStock().intValue()){
                scvo.setQuantity(goods.getStock());
                ShopCart scc = new ShopCart();
                scc.setQuantity(goods.getStock());
                scvo.setLimit_Number(false);
                shopCartMapper.updateByPrimaryKeySelective(scc);
            }else{
                scvo.setQuantity(sc.getQuantity());
                scvo.setLimit_Number(true);
            }
            shopcartvo.add(scvo);
            //写总价计算，写工具类
            if(sc.getChecked()==Const.CK.Check){
                 TotalPrice += BigdecimalUtil.mut(sc.getQuantity().doubleValue(),goods.getPrice().doubleValue());
            }
        }
        //cartVO增加
        cartVo.setShopCartVoList(shopcartvo);
        cartVo.setTotalprice(TotalPrice);
        return  cartVo;
    }

    @Override
    public int deleteByIds(String[] productIds, int userid) {
        int number = shopCartMapper.deleteByIds(productIds,userid);
        return number;
    }

    @Override
    public int setCheckedstatus(String kind, Integer userid, Integer productid) {
        int number;
        String kind1 = kind.trim();
        if(kind1.equals("selectone")){
           number = shopCartMapper.selectone(userid,productid);
            return number;
        }else if(kind1.equals("selectall")){
            number = shopCartMapper.selectall(userid);
            return number;
        }else if(kind1.equals("unselect")){
              number = shopCartMapper.unselect(userid);
              return number;
        }

        number = shopCartMapper.unselectone(userid,productid);
        return number;

    }


}
