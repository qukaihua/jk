package com.qu.controllerback;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.security.rsa.RSASignature;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/28 0028.
 */
@Controller
@RequestMapping("order")
@ResponseBody
public class OrderController  {
    @Autowired
    IOrderService iOrderService;

    @RequestMapping("pay")
    public String pay(int orderno, HttpSession session){
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user) {
            return JSONObject.toJSONString(Result.getfailed("need login"));
        }
        System.out.println("orderno="+orderno);
       String path = session.getServletContext().getRealPath("upload");
        return iOrderService.pay(user.getId(),orderno,path);
    }

    @RequestMapping("callback")
    public String callback(HttpServletRequest request){
        System.out.print("dfsdfsk");
        Map mapparam = request.getParameterMap();
        Map<String,String> map = new HashMap<String,String>();
        Iterator iterator = mapparam.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = "";
            String[] values = (String[]) entry.getValue();
            for(int i=0;i<values.length;i++){
                if(i==0){
                    value = values[i];
                }else {
                    value += values[i];
                }
            }
            map.put(key,value);
        }
        map.remove("sign_type");
        try {
            Boolean alisignure = AlipaySignature.rsaCheckV2(map,Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(alisignure==false){
                return JSONObject.toJSONString(Result.getfailed("sigaure fail"));
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
       Result result = iOrderService.CheckParams(map);
        if(result.getFlag()==1){
            return "failed";
        }
        return "success";

    }
}