package org.example.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;


@Data
public class UserRegister {
    @NotNull(message = "用户名不能为空")
    @Size(max=20,message = "用户名长度不能超过20")
    private String username;//用户名

    @NotNull(message = "用户密码不能为空")
    @Size(min = 6,max = 20,message = "用户密码长度必须在6到20位之间")
    private String password;//密码

    @NotNull(message = "用户身份不能为空")
    @Range(min = 1,max = 3, message = "用户身份不符合规定")
    private Integer identity;//身份（1：管理员；2：老师；3：学生）

    @NotNull(message = "电话号码不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码长度必须为11位")
    private String phone;//电话号码

    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$",message = "验证码长度必须为6位")
    private String code;
}
