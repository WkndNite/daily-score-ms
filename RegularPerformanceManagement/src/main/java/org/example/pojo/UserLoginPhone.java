package org.example.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserLoginPhone {
    @NotNull(message = "电话号码不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码长度必须为11位")
    private String phone;//电话号码

    @NotNull(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$",message = "验证码长度必须为6位")
    private String code;
}
