package com.qu.dao;

import com.qu.bean.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    Order selectbyuseridorderno(@Param("userid") int userid, @Param("orderno") int orderno);
    Order selectbyorderno(int orderno);
}