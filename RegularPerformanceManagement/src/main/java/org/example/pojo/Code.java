package org.example.pojo;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Code {
    private String code;

    @NotNull(message = "电话号码不能为空")
    @Pattern(regexp = "^1\\d{10}$",message = "电话号码长度必须为11位")
    private String phone;

    private LocalDateTime updateTime;
}

