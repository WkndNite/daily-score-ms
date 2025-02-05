package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Course {
    private Integer courseId;//课程标识

    @NotNull(message = "课程名不能为空")
    @Size(max = 40,message = "课程名长度不能超过40")
    private String courseName;//课程名

    @JsonIgnore
    private String username;//用户名

    @Size(max = 50,message = "学校名长度不能超过50")
    private String school;//学校

    @Size(max = 50,message = "学院名长度不能超过50")
    private String college;//学院

    @Size(max = 12,message = "学期名长度不能超过12")
    private String term;//学期

    @Size(max = 20,message = "老师姓名长度不能超过20")
    private String teacherName;//老师姓名

    @NotNull(message = "平时成绩占比不能为空")
    @Range(min = 0, max = 100, message = "平时成绩占比百分比必须在0到100之间")
    private Integer usual;//平时成绩占比

    @NotNull(message = "签到占比不能为空")
    @Range(min = 0, max = 100, message = "签到占比百分比必须在0到100之间")
    private Integer sign;//签到占比

    @NotNull(message = "作业占比不能为空")
    @Range(min = 0, max = 100, message = "作业占比百分比必须在0到100之间")
    private Integer homework;//作业占比

    @NotNull(message = "实验占比不能为空")
    @Range(min = 0, max = 100, message = "实验占比百分比必须在0到100之间")
    private Integer experiment;//实验占比

    @NotNull(message = "选做占比不能为空")
    @Range(min = 0, max = 100, message = "选做占比百分比必须在0到100之间")
    private Integer optional;//选做占比

    @JsonIgnore
    private Integer signNumber;//签到总次数

    @JsonIgnore
    private Integer homeworkNumber;//作业总次数

    @JsonIgnore
    private Integer experimentNumber;//实验总次数

    @JsonIgnore
    private Integer optionalNumber;//选做总次数

    @JsonIgnore
    private String tableName;//对应学生表名
}
