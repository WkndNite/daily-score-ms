package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Usual {
    @JsonView(View.ReturnUsual.class)
    private Integer usualId;//平时成绩标识

    @NotNull(message = "课程标识不可为空")
    private Integer courseId;//课程标识

    @NotBlank(message = "平时成绩类型不可为空")
    @Pattern(regexp = "签到|实验|作业|选做", message = "平时成绩类型错误")
    private String type;//平时成绩类型

    @JsonView(View.ReturnUsual.class)
    private String name;//平时成绩对应字段名

    @JsonView(View.ReturnUsual.class)
    @NotNull(message = "平时成绩占比不可为空")
    @Range(min = 1, max = 100, message = "占比百分比必须在0到100之间")
    private Integer proportion;//占比

    @JsonView(View.ReturnUsual.class)
    @Size(max = 80,message = "备注最多为40个字")
    private String comment;//备注
}
