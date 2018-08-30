package com.qu.service;

import com.qu.common.Result;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/28 0028.
 */
public interface IOrderService {
    public String pay(int userid,int orderno,String path);
    public Result CheckParams(Map<String,String> map);
}