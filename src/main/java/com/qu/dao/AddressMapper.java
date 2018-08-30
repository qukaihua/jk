package com.qu.dao;

import com.qu.bean.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    int deletebyuseridandid(@Param("userid")int userid, @Param("addressid") int addressid);

    List<Address> selectall();
}