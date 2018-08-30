package com.qu.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.bean.Goods;
import com.qu.dao.GoodsMapper;
import com.qu.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by Administrator on 2018/6/14 0014.
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
     @Autowired
    GoodsMapper goodsMapper;
    @Override
    public int insert(Goods goods) {
        int number = goodsMapper.insertSelective(goods);
        return number;
    }

    @Override
    public int update(Goods goods) {
        int number = goodsMapper.updateByPrimaryKeySelective(goods);
        return number;
    }

    @Override
    public int updatestatus(Goods goods) {
        int number = goodsMapper.updateByPrimaryKeySelective(goods);
        return number;
    }

    public PageInfo listgoods(int pagenumber,int pagesize){
        PageHelper.startPage(pagenumber,pagesize);
        List<Goods> goodsal = (List<Goods>) goodsMapper.selectallgoods();
        PageInfo<Goods>pageinfo = new PageInfo<Goods>(goodsal);
        return pageinfo;
    }

    @Override
    public PageInfo searchgoods(Goods goods, int pagenumber, int pagesize) {
        PageHelper.startPage(pagenumber,pagesize);
        List<Goods> goodsal = goodsMapper.searchgoods(goods);
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsal);
        return pageInfo;
    }

    @Override
    public PageInfo searchcdgoods(Set<Integer> CategoryIdSet, String keyword,int pagenumber,int pagesize,String orderby) {
        String[] sort = orderby.split("-");
        PageHelper.startPage(pagenumber,pagesize);
        PageHelper.orderBy(sort[0]+" "+sort[1]);
        List<Goods> goodsList = goodsMapper.checkcdgoods(CategoryIdSet,keyword);
        PageInfo pageInfo = new PageInfo(goodsList);
        return  pageInfo;
    }
}