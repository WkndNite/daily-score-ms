package org.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.example.pojo.Result;
import org.example.pojo.Usual;
import org.example.pojo.UsualUpdate;
import org.example.pojo.View;
import org.example.service.CourseService;
import org.example.service.UsualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
public class UsualController {
    @Autowired
    UsualService usualService;
    @Autowired
    CourseService courseService;

    //添加一个平时成绩组成部分
    @PostMapping("/usual/add")
    public Result addUsual(@Validated @RequestBody Usual usual, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        List<Map<String,String>> usualTypes;
        try {
            usualTypes=courseService.getUsualType(usual.getCourseId());
        }catch (Exception e){
            return Result.error("添加失败");
        }

        boolean isValidType = usualTypes.stream().anyMatch(map -> usual.getType().equals(map.get("type")));

        if(!isValidType){
            return Result.error("平时成绩类型错误");
        }

        try {
            usualService.addUsual(usual);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("添加失败");
        }

        return Result.success();
    }

    //查询一个课程的平时成绩组成部分
    @PostMapping("/usual/get")
    @JsonView(View.ReturnUsual.class)
    public Result getUsual(@RequestBody Integer courseId){
        List<Usual> usuals;
        try{
            usuals=usualService.getUsual(courseId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("查询失败");
        }
        return Result.success(usuals);
    }

    //修改一个平时成绩组成部分
    @PostMapping("/usual/update")
    public Result updateUsual(@Validated @RequestBody UsualUpdate usualUpdate, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return Result.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        try{
            usualService.updateUsual(usualUpdate);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("修改失败");
        }

        return Result.success();
    }

    //删除一个平时成绩组成部分
    @PostMapping("/usual/delete")
    public Result deleteUsual( @RequestBody Integer usualId){
        try {
            usualService.deleteUsual(usualId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("删除失败");
        }
        return Result.success();
    }


}
