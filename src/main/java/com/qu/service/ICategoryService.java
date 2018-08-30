package com.qu.service;

import com.qu.bean.Category;
import java.util.*;

/**
 * Created by Administrator on 2018/6/13 0013.
 */
public interface ICategoryService {
    public int AddCategory(Category cg);
    public int UpdateCategory(Category cg);
    public List<Category> getcg(int CategoryId);
    public Set<Integer> getallchild(int CategoryId,Set<Integer> categoryset);
    public Category getonecg(Integer Categoryid);
}