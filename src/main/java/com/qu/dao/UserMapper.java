package com.qu.dao;

import com.qu.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    Integer  selectUser(@Param("name") String name);
    User selectUserByPassword(@Param("name")String name,@Param("password")String password);
    int selectemail(@Param("email") String email);
    String selectquestion(String username);
    int updatepassword(@Param("password") String password,@Param("username") String username);
    int selectuserpassword(@Param("name") String name,@Param("password") String password);
    int selectanswer(@Param("username") String username,@Param("answer") String answer );
}