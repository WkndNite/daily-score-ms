package org.example.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginAccount {
    @NotNull(message = "用户名不能为空")
    @Size(max=20,message = "用户名长度不能超过20")
    private String username;//用户名

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6,max = 20,message = "用户密码长度必须在6到20位之间")
    private String password;//密码
}
