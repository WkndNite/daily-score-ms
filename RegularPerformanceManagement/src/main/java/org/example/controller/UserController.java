package org.example.controller;

import org.example.pojo.*;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    //注册
    @PostMapping("/user/register")
    public Result register(@Validated @RequestBody UserRegister userRegister, BindingResult bindingResult) {
        //参数校验
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        UserRegister u1 =userService.findUserByUsername(userRegister.getUsername());
        if(u1 !=null) {
            //用户名被占用
            return Result.error("用户名已存在");
        }

        UserRegister u2=userService.findUserByPhone(userRegister.getPhone());
        if(u2 !=null) {
            //手机号已经绑定至其他账号
            return Result.error("该手机号已经绑定到其他账号");
        }

        //用户名不存在
        Code c = userService.getCode(userRegister.getPhone());
        if(c!=null) {
            //检验验证码是否正确
            if(c.getCode().equals(userRegister.getCode())) {
                if(LocalDateTime.now().isBefore(c.getUpdateTime().plusMinutes(5))){
                    userService.register(userRegister);
                    return Result.success();
                }else{
                    return Result.error("短信验证码过期");
                }
            }else {
                return Result.error("短信验证码错误");
            }
        }else {
            return Result.error("请先向该手机号发送短信验证码");
        }
    }

    //账密登录
    @PostMapping("/user/loginAccount")
    public Result loginAccount(@Validated @RequestBody UserLoginAccount userLoginAccount, BindingResult bindingResult) {
        //参数校验
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        UserRegister u=userService.findUserByUsername(userLoginAccount.getUsername());
        if(u==null){
            return Result.error("用户名不存在");
        }else{
            if(u.getPassword().equals(Md5Util.getMD5String(userLoginAccount.getPassword()))){
                Map<String,Object> claims=new HashMap<>();
                claims.put("username",u.getUsername());
                String token= JwtUtil.genToken(claims);

                Map<String,Object> map=new HashMap<>();
                map.put("token",token);
                map.put("identity",u.getIdentity());

                return Result.success(map);
            }else{
                return Result.error("密码错误");
            }
        }
    }

    //短信验证登录
    @PostMapping("user/loginPhone")
    public Result loginPhone(@Validated @RequestBody UserLoginPhone userLoginPhone, BindingResult bindingResult) {
        //参数校验
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        UserRegister u=userService.findUserByPhone(userLoginPhone.getPhone());
        if(u==null){
            return Result.error("手机号错误");
        }else{
            Code c = userService.getCode(userLoginPhone.getPhone());
            if(c!=null) {
                //存在对应手机号的验证码，检验验证码是否正确
                if(c.getCode().equals(userLoginPhone.getCode())) {
                    //验证码正确，检验是否过期
                    if(LocalDateTime.now().isBefore(c.getUpdateTime().plusMinutes(5))){
                        //验证码仍然有效，有效期为5分钟
                        Map<String,Object> claims=new HashMap<>();
                        claims.put("username",u.getUsername());
                        String token= JwtUtil.genToken(claims);

                        Map<String,Object> map=new HashMap<>();
                        map.put("token",token);
                        map.put("identity",u.getIdentity());

                        return Result.success(map);
                    }else{
                        //验证码已过期
                        return  Result.error("短信验证码已过期");
                    }
                }else{
                    return Result.error("短信验证码错误");
                }
            }else{
                //不存在对应手机号的验证码
                return Result.error("请先向该手机号发送短信验证码");
            }
        }
    }

    //获取短信验证码
    @PostMapping("/user/code")
    public Result code(@Validated @RequestBody Code code, BindingResult bindingResult) {
        //参数校验
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        Code c=userService.getCode(code.getPhone());
        //如果c不为空，即之前已经向该手机号发送过验证码
        if(c!=null){
            LocalDateTime localDateTime=LocalDateTime.now();
            //如果距离上次发送还不超过两分钟，则提示稍后再次尝试
            if(localDateTime.isBefore(c.getUpdateTime().plusMinutes(2))){
                return Result.error("2分钟内不可重复发送验证码，请稍后再试");
            }
        }

        //生成一个随机的验证码
        SecureRandom secureRandom = new SecureRandom();
        String randomNum=String.format("%06d", secureRandom.nextInt(1000000));

        SmsUtil smsUtil = new SmsUtil();
        //发送短信验证码
        if(smsUtil.sendSms(code.getPhone(),"SMS_478605142","{\"code\":\""+randomNum+"\"}")){
            //将手机号和验证码写入数据库
            code.setCode(randomNum);
            if(c!=null){
                //如果该手机号的验证码在数据库中有存储，则进行更新
                //！！！存在原子性问题！！！可能会出现异常
                userService.updateCode(code);
            }else{
                //如果该手机号的验证码在数据库中没有存储，则进行添加
                userService.addCode(code);
            }

            Map<String,Object> map=new HashMap<>();
            map.put("code",randomNum);

            return Result.success(map);
        }else{
            return Result.error("短信验证码发送失败");
        }
    }

    //删除一个用户

    //修改用户的个人信息
}
