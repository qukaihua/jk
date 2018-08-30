package com.qu.controllerback;

import com.alibaba.druid.support.logging.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.qu.bean.Category;
import com.qu.bean.Goods;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.service.ICategoryService;
import com.qu.service.IGoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
@Controller
@RequestMapping("goods")
@ResponseBody
public class GoodsController {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    ICategoryService categoryService;

    @RequestMapping("save")
    public String save(Goods goods, HttpSession session) {
        User user = (User) session.getAttribute(Const.CurrentUser);
        if (null == user || user.getRole() != Const.ROLE.Manager) {
            return JSONObject.toJSONString(Result.getfailed("no manager"));
        }
            if (goods.getId() != null) {
                //save
                goods.setUpdateTime(new Date());
                int number = goodsService.update(goods);
                if (number > 0) {
                    return JSON.toJSONString(Result.getsuccessful("save ok"));
                }
                return JSON.toJSONString(Result.getfailed("save fail"));
              }
            //insert
            goods.setCreateTime(new Date());
            int number = goodsService.insert(goods);
            if (number > 0) {
                return JSON.toJSONString(Result.getsuccessful("update ok"));
            }
            return JSON.toJSONString(Result.getfailed("update fail"));
        }


    @RequestMapping("changegoodsstatus")
    public String changestatus(Goods goods) {
        if (goods == null || goods.getId() == null || goods.getStatus() == null) {
            return JSONObject.toJSONString(Result.getfailed("param error"));
        }
        int number = goodsService.updatestatus(goods);
        if (number > 0) {
            return JSONObject.toJSONString(Result.getsuccessful("update status ok"));
        }
        return JSONObject.toJSONString(Result.getfailed("update status fail"));

    }
    @RequestMapping("listgoods")
    public String listgoods(int pagenumber,int pagesize){
        PageInfo  goodsinfo = goodsService.listgoods(pagenumber,pagesize);
        return JSONObject.toJSONString(Result.getsuccessful("get goods ok",goodsinfo));
    }
   @RequestMapping("searchgoods")
    public String searchgoods(Goods goods,int pagenumber,int pagesize){
       PageInfo pageinfo  = goodsService.searchgoods(goods,pagenumber,pagesize);
       return JSONObject.toJSONString(Result.getsuccessful("search ok",pageinfo));
   }

   @RequestMapping("searchgoodsBycd")
    public String searcgoodsbycd(Integer CategoryId,String keyword,int pagenumber,int pagesize,String orderby){
       System.out.println("ininininn");
       Set<Integer> CategoryidSet = new HashSet<Integer>();
        if(CategoryId==null&&keyword==null){
            return JSONObject.toJSONString(Result.getfailed("param error"));
        }
       if(CategoryId!=null){
           System.out.println("get in");
           Category cg = categoryService.getonecg(CategoryId);
           /*logger.info(cg.getName());*/
           if(cg==null){
               return JSONObject.toJSONString(Result.getfailed("no this category"));
           }
           /*logger.info("进入查询子节点");*/
           CategoryidSet =  categoryService.getallchild(CategoryId.intValue(),CategoryidSet);
           System.out.print(CategoryidSet.size());
       }

        PageInfo pageInfo =goodsService.searchcdgoods(CategoryidSet,keyword,pagenumber,pagesize,orderby);
       return  JSONObject.toJSONString(Result.getsuccessful("search cd goods ok",pageInfo));
   }

}