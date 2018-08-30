package com.qu.dao;

import com.qu.bean.ShopCart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShopCart record);

    int insertSelective(ShopCart record);

    ShopCart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShopCart record);

    int updateByPrimaryKey(ShopCart record);

    ShopCart selectbyuseridpid(@Param("userid") int userid, @Param("productid") int productid);

    int updateshopcartnumber(@Param("entity") ShopCart sc);
    List<ShopCart> selecbyuserid(int userid);
    int deleteByIds(@Param("productIds") String[]productIds,@Param("userid") int userid);
    int selectone(@Param("userid") int userid,@Param("productid") int productid);
    int unselectone(@Param("userid") int userid,@Param("productid") int productid);
    int selectall(@Param("userid") int userid);
    int unselect(@Param("userid") int userid);
}