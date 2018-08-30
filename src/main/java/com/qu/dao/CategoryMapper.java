package com.qu.dao;

import com.qu.bean.Category;
import org.apache.ibatis.annotations.Param;
import java.util.*;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    List<Category> selectchild(@Param("CategoryId") int CategoryId);
}