package com.qu.controllerback;

import com.alibaba.fastjson.JSONObject;
import com.qu.bean.ShopCart;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.service.IShopcartService;
import com.qu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2018/6/22 0022.
 */
@Controller
@RequestMapping("shopcart")
@ResponseBody
public class ShopcartController {
    @Autowired
    IShopcartService shopcartService;

    @RequestMapping("add")
    public String add(ShopCart sc, HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        sc.setUserId(user.getId());
        return shopcartService.addshopcart(sc);
    }

    @RequestMapping("update")
    public String update(ShopCart sc, HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        sc.setUserId(sc.getUserId());
        int number = shopcartService.updatecartnumber(sc);
        if (number > 0) {
            CartVo cartVo = shopcartService.getCartVo(sc.getUserId());
            return JSONObject.toJSONString(Result.getsuccessful("update number ok", cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("update fail"));
    }

    @RequestMapping("delete")
    public String delete(String productIds, HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        String[] productIds1 = productIds.split(",");
        System.out.print(productIds1);
        int number = shopcartService.deleteByIds(productIds1, user.getId());
        if (number > 0) {
            CartVo cartVo = shopcartService.getCartVo(user.getId());
            return JSONObject.toJSONString(Result.getsuccessful("delete ok", cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("delete fail"));
    }

    @RequestMapping("selectall")
    public String selectall(HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        int number = shopcartService.setCheckedstatus("selectall",user.getId(),null);
        if(number>0){
            CartVo cartVo = shopcartService.getCartVo(user.getId());
            return JSONObject.toJSONString(Result.getsuccessful("set checked ok",cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("set checked fail"));
    }

    @RequestMapping("unselect")
    public String unselect(HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        int number = shopcartService.setCheckedstatus("unselect",user.getId(),null);
        if(number>0){
            CartVo cartVo = shopcartService.getCartVo(user.getId());
            return JSONObject.toJSONString(Result.getsuccessful("set checked ok",cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("set checked fail"));
    }


    @RequestMapping("selectone")
    public String selectone(int productId,HttpSession session){
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        int number = shopcartService.setCheckedstatus("selectone",user.getId(),productId);
        if(number>0){
            CartVo cartVo = shopcartService.getCartVo(user.getId());
            return JSONObject.toJSONString(Result.getsuccessful("set checked ok",cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("set checked fail"));
    }

    @RequestMapping("unselectone")
    public String unselectone(int productId,HttpSession session){
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        int number = shopcartService.setCheckedstatus("unselectone",user.getId(),productId);
        if(number>0){
            CartVo cartVo = shopcartService.getCartVo(user.getId());
            return JSONObject.toJSONString(Result.getsuccessful("set checked ok",cartVo));
        }
        return JSONObject.toJSONString(Result.getfailed("set checked fail"));
    }

}