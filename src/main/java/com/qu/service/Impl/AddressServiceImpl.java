package com.qu.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qu.bean.Address;
import com.qu.dao.AddressMapper;
import com.qu.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/6/25 0025.
 */
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    AddressMapper addressMapper;

    @Override
    public int addaddress(Address address) {
        int number = addressMapper.insert(address);
        System.out.print("serviceid"+address.getId());
        return number;
    }

    @Override
    public int deleteaddress(int userid, int addressid) {
        int number = addressMapper.deletebyuseridandid(userid,addressid);
        return number;
    }

    @Override
    public int updateaddress(Address address) {
        int number = addressMapper.updateByPrimaryKey(address);
        return number;
    }

    @Override
    public Address select(int addressid) {
        Address address = addressMapper.selectByPrimaryKey(addressid);
        return address;
    }

    @Override
    public PageInfo listaddress(int pagenumber, int pagesize) {
        PageHelper.startPage(pagenumber,pagesize);
        List<Address> addresslist = addressMapper.selectall();
        PageInfo pageInfo = new PageInfo(addresslist);
        return pageInfo;
    }
}