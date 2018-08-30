package com.qu.service.Impl;

import com.qu.bean.Category;
import com.qu.dao.CategoryMapper;
import com.qu.service.ICategoryService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/6/13 0013.
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    public int AddCategory(Category cg) {
        int number = categoryMapper.insert(cg);
        return number;
    }

    @Override
    public int UpdateCategory(Category cg) {
        int number = categoryMapper.updateByPrimaryKeySelective(cg);
        return number;
    }

    @Override
    public List<Category> getcg(int CategoryId) {
        List<Category> cgal = (ArrayList<Category>) categoryMapper.selectchild(CategoryId);
        return cgal;
    }

    @Override
    public Set<Integer> getallchild(int CategoryId, Set<Integer> categoryset){
         categoryset.add(CategoryId);
        List<Category> chidlist = categoryMapper.selectchild(CategoryId);
        if(null!=chidlist&&chidlist.size()>0){
        for(Category cg:chidlist){
            this.getallchild(cg.getId(),categoryset);
        }
        }
        return categoryset;

    }

    public Category getonecg(Integer Categoryid){
        return categoryMapper.selectByPrimaryKey(Categoryid);
    }




}