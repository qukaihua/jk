package com.qu.service;

import com.github.pagehelper.PageInfo;
import com.qu.bean.Address;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25 0025.t
 */
public interface IAddressService {
    public int addaddress(Address address);
    public int deleteaddress(int userid,int addressid);
    public int updateaddress(Address address);
    public Address select(int addressid);
    public PageInfo listaddress(int pagenumber, int pagesize);
}