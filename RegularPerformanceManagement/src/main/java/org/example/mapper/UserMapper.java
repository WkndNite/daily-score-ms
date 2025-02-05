package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.Code;
import org.example.pojo.UserRegister;

@Mapper
public interface UserMapper {
    //根据用户名查询用户名是否被占用
    @Select("select * from user where username=#{username}")
    UserRegister findUserByUsername(String username);
    //注册
    @Insert("insert into user(username,password,phone,identity) values(#{username},#{password},#{phone},#{identity})")
    void register(UserRegister userRegister);
    //存储验证码
    @Insert("insert into code(phone,code) values(#{phone},#{code})")
    void addCode(Code code);
    //获取验证码
    @Select("select * from code where phone=#{phone}")
    Code getCode(String phone);
    //更新验证码
    @Update("update code set code=#{code} where phone=#{phone}")
    void updateCode(Code code);
    //根据电话号码查询用户名是否被占用
    @Select("select * from user where phone=#{phnoe}")
    UserRegister findUserByPhone(String phone);
}
