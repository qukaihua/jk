package com.qu.service;

import com.qu.bean.User;

/**
 * Created by 瞿凯华 on 2018/6/6 0006.
 */
public interface IUserService {
    public Integer CheckUser(String name);
    public User CheckUserByPassword(String name,String password);
    public int Checkemail(String email);
    public int Adduser(User user);
    public String Checkquestion(String username);
    public int UpdatePassword(String password,String username);
    public int CheckPassword(String name,String password);
    public int Checkanswer(String username,String answer);
}
