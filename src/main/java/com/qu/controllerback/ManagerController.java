package com.qu.controllerback;

import com.alibaba.fastjson.JSONObject;
import com.qu.bean.Category;
import com.qu.bean.User;
import com.qu.common.Const;
import com.qu.common.Result;
import com.qu.dao.CategoryMapper;
import com.qu.service.ICategoryService;
import com.qu.service.Impl.CategoryServiceImpl;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/13 0013.
 */
@Controller
@RequestMapping("manager")
@ResponseBody
public class ManagerController {
    Logger logger = Logger.getLogger(ManagerController.class);
    @Autowired
    ICategoryService categoryService;
    @RequestMapping("addcategory")
    public String AddCategory(Category cg){
      /*  User user = (User) session.getAttribute(Const.CurrentUser);
        if(null==user||user.getRole()!=Const.ROLE.Manager){
            return JSONObject.toJSONString(Result.getfailed("no manager"));
        }*/
        cg.setCreateTime(new Date());
        int number = categoryService.AddCategory(cg);
        if(number>0){
            return JSONObject.toJSONString(Result.getsuccessful("add category ok"));
        }
        return JSONObject.toJSONString(Result.getfailed("add category fail"));
    }

    @RequestMapping("changecg")
    public String Changecg(Category cg){
        System.out.print(cg.getName());
       /* User user = (User) session.getAttribute(Const.CurrentUser);
        if(null==user||user.getRole()!=Const.ROLE.Manager){
            return JSONObject.toJSONString(Result.getfailed("no manager"));
        }*/
        if(cg==null){
            return JSONObject.toJSONString(Result.getfailed("cg is null"));
        }
        cg.setUpdateTime(new Date());
        int number = categoryService.UpdateCategory(cg);
        if(number>0){
            return JSONObject.toJSONString(Result.getsuccessful("change cg ok"));
        }
        return JSONObject.toJSONString(Result.getfailed("change cg fail"));
    }

    @RequestMapping("listchildcg")
    public String listchild(int categoryid){
        List<Category> cgal = (ArrayList<Category>) categoryService.getcg(categoryid);
        if(cgal!=null&& cgal.size()>0){
            return JSONObject.toJSONString(Result.getsuccessful("child cg get",cgal));
        }
        logger.info("已是父节点");
        return  JSONObject.toJSONString(Result.getfailed("already father jd"));
    }

    @RequestMapping("listallchildid")
    public String ListallchildId(int CategoryId){
        Set<Integer> categoryset = new HashSet<Integer>();
        Set allchildset = categoryService.getallchild(CategoryId,categoryset);
        return JSONObject.toJSONString(Result.getsuccessful("get all child",allchildset));

    }

}