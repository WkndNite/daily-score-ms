package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class UsualUpdate {
    @NotNull(message = "平时成绩标识不可为空")
    private Integer usualId;//平时成绩标识

    @NotNull(message = "平时成绩占比不可为空")
    @Range(min = 0, max = 100, message = "占比百分比必须在0到100之间")
    private Integer proportion;//占比

    @Size(max = 80,message = "备注最多为40个字")
    private String comment;//备注
}
