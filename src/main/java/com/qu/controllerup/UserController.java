package com.qu.controllerup;

import com.alibaba.fastjson.JSONObject;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.common.TokenCache;
import com.qu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 瞿凯华 on 2018/6/6 0006.
 */
@Controller
@RequestMapping("user")
@ResponseBody
public class UserController {
    @Autowired
   IUserService userService;
    @RequestMapping(value = "login" ,method = {RequestMethod.POST,RequestMethod.GET})
    public String login(String name,String password,HttpSession session) {
        System.out.println(name);
        System.out.println(password);
        Integer number = userService.CheckUser(name);
        if (0 == number) {
            //返回无用户
            /*System.out.print("无用户");

            map.put("code","1");
            map.put("msg","无用户");
            return  map;*/
           /* System.out.println(JSONObject.toJSONString(Result.getfailed("无")));*/
            System.out.println("no user");
            return JSONObject.toJSONString(Result.getfailed("no user"));
        }
        User user = userService.CheckUserByPassword(name, password);
        if (null == user) {
            //返回密码错误;
            return JSONObject.toJSONString(Result.getfailed("password wrong"));
        }
        //返回登录成功
        session.setAttribute(Const.CurrentUser,user);
       return JSONObject.toJSONString(Result.getsuccessful("login ok",user));
    }
    @RequestMapping("register")
    public String register(User user){
        if(userService.CheckUser(user.getUsername())>0){
             return JSONObject.toJSONString(Result.getfailed("user exist"));
         }
        if(userService.Checkemail(user.getEmail())>0){
            return  JSONObject.toJSONString(Result.getfailed("email exist"));
        }
        user.setRole(Const.ROLE.Normal);
       if(userService.Adduser(user)==0){
           return  JSONObject.toJSONString(Result.getfailed("register fail"));
       }
            return  JSONObject.toJSONString(Result.getsuccessful("register success"));

    }

    @RequestMapping("checkvalid")
    public String checkvalid(String body,String type){
        if(!StringUtils.isEmpty(type)) {
            if (type.equals(Const.T_UserName)) {
                Integer number = userService.CheckUser(body);
                if (number > 0) {
                    return JSONObject.toJSONString(Result.getfailed("user exist"));
                }
            }
            if (type.equals(Const.T_Email)) {
                int number = userService.Checkemail(body);
                if (number > 0) {
                    return JSONObject.toJSONString(Result.getfailed("email exist"));
                }
            }
        } else {
            return JSONObject.toJSONString(Result.getfailed("param exist"));
        }
        return JSONObject.toJSONString(Result.getsuccessful("check ok"));

    }
    @RequestMapping("forgetquestion")
    public String forgetquestion(String username){
        if(userService.CheckUser(username)==0){
            return  JSONObject.toJSONString(Result.getfailed("no user"));
        }
        String question = userService.Checkquestion(username);
        if(StringUtils.isEmpty(question)){
            return JSONObject.toJSONString(Result.getfailed("question none"));
        }
        return JSONObject.toJSONString(Result.getsuccessful("question ok",question));

    }

    @RequestMapping("checkquestion")
    public String checkquestion(String question,String answer,String username){
          int number = userService.Checkanswer(username,answer);
        if(number>0){
            //回答问题正确，返回token
            String token = UUID.randomUUID().toString();
            TokenCache.setKey(username,token);
            return JSONObject.toJSONString(Result.getsuccessful("answer right",token));
        }

        return JSONObject.toJSONString(Result.getfailed("wrong answer"));

    }

    @RequestMapping("forgetsetpd")
    public String forgetsetpd(String password,String forgettoken,String username){
        if(StringUtils.isEmpty(forgettoken)){
            return JSONObject.toJSONString("token错误");
        }
        //获取对应的token
        String token = TokenCache.getKey(username);

        if(forgettoken.equals(token)){
            int number = userService.UpdatePassword(password,username);
            if(number>0){
                return JSONObject.toJSONString(Result.getsuccessful("set pd success"));
            }
        }

        return JSONObject.toJSONString(Result.getfailed("set pd fail"));
    }

    //登录过程中修改密码
    @RequestMapping("resetpassword")
    public  String resetpassword(String username,String oldpd,String newpd){
        System.out.println(username);
        System.out.println(oldpd);
        int number = userService.CheckPassword(username,oldpd);
        System.out.println(number);
        if(number>0){
            System.out.println("in ok");
            int number2 = userService.UpdatePassword(newpd,username);
            if(number2>0){
                return JSONObject.toJSONString(Result.getfailed("setpassword ok"));
            }
        }
        return JSONObject.toJSONString(Result.getfailed("oldpassword wrong"));
    }
    //登录过程中修改个人信息
   /* @RequestMapping("resetinfo")
    public String resetinfo(User user,HttpSession session){
        User user = session.getAttribute(Const.CurrentUser);

    }*/
    //获取登录用户信息
    @RequestMapping("getinfo")
    public String getinfo(HttpSession session){
       User user =(User) session.getAttribute(Const.CurrentUser) ;
        return JSONObject.toJSONString(Result.getsuccessful(user));
    }
    //用户登出
    @RequestMapping("loginout")
    public String loginout(HttpSession session){
        session.removeAttribute(Const.CurrentUser);
        return JSONObject.toJSONString(Result.getsuccessful("移除成功"));

    }
    }




