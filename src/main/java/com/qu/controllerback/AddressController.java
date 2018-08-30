package com.qu.controllerback;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qu.bean.Address;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2018/6/25 0025.
 */
@Controller
@RequestMapping("address")
@ResponseBody
public class AddressController {
      @Autowired
      IAddressService addressService;
      @RequestMapping("add")
      public String addadress(HttpSession session, Address address){
            User user = (User) session.getAttribute(Const.CurrentUser);
            if (null == user) {
                  return JSONObject.toJSONString(Result.getfailed("need login"));
            }
            address.setUserId(user.getId());
          int number =  addressService.addaddress(address);
            System.out.println("controllerid"+address.getId());
          if(number>0){
                return JSONObject.toJSONString(Result.getsuccessful("add ok"));
          }
            return JSONObject.toJSONString(Result.getfailed("add fail"));

      }

          @RequestMapping("delete")
      public String deleteadress(HttpSession session, int addressid){
            User user = (User) session.getAttribute(Const.CurrentUser);
            if (null == user) {
                  return JSONObject.toJSONString(Result.getfailed("need login"));
            }
            int number = addressService.deleteaddress(user.getId(),addressid);
            if(number>0){
                  return JSONObject.toJSONString(Result.getsuccessful("delete ok"));
            }
            return JSONObject.toJSONString(Result.getfailed("delete fail"));

      }

      @RequestMapping("update")
      public String updateadress(HttpSession session, Address address){
            User user = (User) session.getAttribute(Const.CurrentUser);
            if (null == user) {
                  return JSONObject.toJSONString(Result.getfailed("need login"));
            }
            address.setUserId(user.getId());
            int number = addressService.updateaddress(address);
            if(number>0){
                  return JSONObject.toJSONString(Result.getsuccessful("update ok"));
            }
            return JSONObject.toJSONString(Result.getfailed("update fail"));

      }

      @RequestMapping("select")
      public String selectadress(HttpSession session, int addressid){
            User user = (User) session.getAttribute(Const.CurrentUser);
            if (null == user) {
                  return JSONObject.toJSONString(Result.getfailed("need login"));
            }
            Address address = addressService.select(addressid);
            if(null==address){
                  return JSONObject.toJSONString(Result.getfailed("select fail"));
            }
            return JSONObject.toJSONString(Result.getsuccessful("select ok",address));

      }

      @RequestMapping("list")
      public String addadress(HttpSession session, int pagenumber,int pagesize){
            User user = (User) session.getAttribute(Const.CurrentUser);
            if (null == user) {
                  return JSONObject.toJSONString(Result.getfailed("need login"));
            }
            PageInfo pageInfo = addressService.listaddress(pagenumber,pagesize);
            return JSONObject.toJSONString(Result.getsuccessful("list ok",pageInfo));
      }
}