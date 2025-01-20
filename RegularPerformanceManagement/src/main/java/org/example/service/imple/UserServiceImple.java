package org.example.service.imple;

import org.example.mapper.UserMapper;
import org.example.pojo.Code;
import org.example.pojo.UserRegister;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImple implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserRegister findUserByPhone(String phone) {
        return userMapper.findUserByPhone(phone);
    }

    //寻找指定用户
    @Override
    public UserRegister findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    //注册账号
    @Override
    public void register(UserRegister userRegister) {
        //加密
        String md5String= Md5Util.getMD5String(userRegister.getPassword());
        userRegister.setPassword(md5String);
        //添加
        userMapper.register(userRegister);
    }

    //添加验证码
    @Override
    public void addCode(Code code) {
        userMapper.addCode(code);
    }

    //获取验证码时间
    @Override
    public Code getCode(String phone) {
        return userMapper.getCode(phone);
    }

    //更新验证码
    @Override
    public void updateCode(Code code) {
        userMapper.updateCode(code);
    }
}
