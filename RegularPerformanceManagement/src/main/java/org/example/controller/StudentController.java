package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;

    //查询学生表中的数据
    @PostMapping("/student/get")
    public Result getStudent(@RequestBody Integer courseId) {
        List<Map<String,Object>> students;
        try{
            students=studentService.getStudent(courseId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("查询失败");
        }

        return Result.success(students);
    }

    //向学生表中插入数据
    @PostMapping("/student/change")
    public Result insertStudent(@RequestBody Student studentTemp){
        Integer courseId = studentTemp.getCourseId();
        List<Map<String,Object>> students=studentTemp.getStudents();

        if(students.isEmpty()){
            return Result.error("数据不可为空");
        }

        for(Map<String,Object> student:students){
            if((student.get("id")==null)||(student.get("name")==null)){
                return Result.error("学号和学生姓名不能为空");
            }
        }
        try {
            studentService.insertStudent(students,courseId);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("插入失败");
        }
        return Result.success();
    }

    //批量删除学生中的数据
    @PostMapping("/student/delete")
    public Result deleteStudent(@RequestBody Student studentTemp){
        Integer courseId = studentTemp.getCourseId();
        List<String> ids=studentTemp.getIds();
        try{
            studentService.deleteStudent(courseId,ids);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Result.error("删除失败");
        }

        return Result.success();
    }
}
