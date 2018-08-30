package com.qu.service;

import com.github.pagehelper.PageInfo;
import com.qu.bean.Goods;

import java.util.Set;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
public interface IGoodsService {
    public int insert(Goods goods);
    public int update(Goods goods);
    public int updatestatus(Goods goods);
    public PageInfo listgoods(int pagenumber,int pagesize);
    public PageInfo searchgoods(Goods goods,int pagenumber,int pagesize);
    public PageInfo searchcdgoods(Set<Integer> CategoryIdSet, String keyword,int pagenumber,int pagesize,String orderby);
}