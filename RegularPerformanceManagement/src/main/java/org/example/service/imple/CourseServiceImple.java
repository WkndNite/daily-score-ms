package org.example.service.imple;

import org.example.mapper.CourseMapper;
import org.example.pojo.Course;
import org.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImple implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Override
    public void addCourse(Course course) {
        //为course的各平时成绩部分的总次数赋值为0
        course.setSignNumber(0);
        course.setHomeworkNumber(0);
        course.setExperimentNumber(0);
        course.setOptionalNumber(0);
        //调用Mapper层的添加方法
        courseMapper.addCourse(course);
        //添加生成对应学生表的表名
        String tableName="student"+course.getCourseId();
        course.setTableName(tableName);
        courseMapper.setTableName(course);
        //编写sql语句，调用Mapper层创建一个对应的学生表
        courseMapper.createStudentTable(tableName);
        //为该学生表创建触发器,每次修改都会使得new字段变为false
        courseMapper.createTriggerForStudent(tableName);
        //为学生表创建触发器，使得总成绩字段没有赋值时自动为
    }

    @Override
    public List<Course> getCourses(String username) {
        return courseMapper.getCourses(username);
    }

    @Override
    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    @Override
    public List<Map<String,String>> getUsualType(Integer courseId) {
        Course course=courseMapper.getCourseByCourseId(courseId);
        List<Map<String,String>> usualTypes= new ArrayList<>();
        if(course.getSign()!=0){
            usualTypes.add(Map.of("type", "签到"));
        }
        if(course.getHomework()!=0){
            usualTypes.add(Map.of("type", "作业"));
        }
        if(course.getExperiment()!=0){
            usualTypes.add(Map.of("type", "实验"));
        }
        if(course.getOptional()!=0){
            usualTypes.add(Map.of("type", "选做"));
        }
        return usualTypes;
    }

    @Override
    public void deleteCourse(Integer courseId) {
        Course course=courseMapper.getCourseByCourseId(courseId);
        courseMapper.deleteStudentTable(course.getTableName());
        courseMapper.deleteCourse(courseId);
    }
}
