package org.example.controller;

import org.example.pojo.Course;
import org.example.pojo.Result;
import org.example.service.CourseService;
import org.example.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;

    //添加一个课程
    @PostMapping("/course/add")
    public Result addCourse(@RequestHeader(name = "token") String token, @Validated @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        //各平时成绩组成部分占比和为1
        if(course.getSign()+course.getHomework()+course.getExperiment()!=100){
            return Result.error("签到+实验+作业占比的和必须为1");
        }

        //获取请求头中的用户名
        Map<String,Object> map = JwtUtil.parseToken(token);
        String username=map.get("username").toString();
        course.setUsername(username);

        //调用Service层
        try {
            courseService.addCourse(course);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("课程创建失败");
        }

        return Result.success();
    }

    //修改一个课程
    @PostMapping("/course/update")
    public Result updateCourse(@Validated @RequestBody Course course, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        if(course.getCourseId()==null){
            Result.error("课程标识不能为空");
        }

        //各平时成绩组成部分占比和为1
        if(course.getSign()+course.getHomework()+course.getExperiment()!=100){
            return Result.error("签到+实验+作业占比的和必须为1");
        }

        try{
            courseService.updateCourse(course);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("课程信息修改失败");
        }
        return Result.success();
    }

    //根据一个课程标识删除一个课程
    @PostMapping("/course/deleteCourse")
    public Result deleteCourse(@RequestBody Integer courseId){
        try{
            courseService.deleteCourse(courseId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("删除课程失败");
        }
        return Result.success();
    }

    //返回一个老师的所有课程
    @GetMapping("/course/get")
    public Result getCourse(@RequestHeader(name = "token") String token) {
        //获取请求头中的用户名
        Map<String,Object> map = JwtUtil.parseToken(token);
        String username=map.get("username").toString();

        List<Course> courses=courseService.getCourses(username);
        return Result.success(courses);
    }

    //返回一个课程的平时成绩类型
    @PostMapping("/course/getUsualName")
    public Result getUsualName(@RequestBody Integer courseId) {
        List<Map<String,String>> usualTypes;
        try{
            usualTypes=courseService.getUsualType(courseId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("获取失败");
        }

        return Result.success(usualTypes);
    }
}
