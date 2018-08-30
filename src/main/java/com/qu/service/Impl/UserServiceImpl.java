package com.qu.service.Impl;

import com.qu.bean.User;
import com.qu.dao.UserMapper;
import com.qu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 瞿凯华 on 2018/6/6 0006.
 */
@Service
public class UserServiceImpl implements IUserService{


    @Autowired
    UserMapper usermapper;
    public Integer CheckUser(String name) {
     Integer number = usermapper.selectUser(name);
        return number;
    }

    @Override
    public User CheckUserByPassword(String name,String password) {
        User user = usermapper.selectUserByPassword(name,password);
        return user;
    }

    @Override
    public int Checkemail(String email) {
        int number = usermapper.selectemail(email);
        return number;
    }

    @Override
    public int Adduser(User user) {
         int number = usermapper.insert(user);
        return number;
    }

    public String Checkquestion(String username){
        String question = usermapper.selectquestion(username);
        return question;
    }

    public int UpdatePassword(String password,String username){
        int number = usermapper.updatepassword(password,username);
        return number;
    }

    @Override
    public int CheckPassword(String name, String password) {
         int number = usermapper.selectuserpassword(name,password);
        return number;
    }

    @Override
    public int Checkanswer(String username, String answer) {
         int number = usermapper.selectanswer(username,answer);
        return number;
    }
}
