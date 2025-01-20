package org.example.service;

import org.example.pojo.Code;
import org.example.pojo.UserRegister;


public interface UserService {
    //根据手机号查询用户是否存在
    UserRegister findUserByPhone(String phone);
    //根据用户名查询用户是否存在
    UserRegister findUserByUsername(String username);
    //注册
    void register(UserRegister userRegister);
    //添加验证码
    void addCode(Code code);
    //获取指定手机号的验证码
    Code getCode(String phone);
    //更新验证码
    void updateCode(Code code);
}
