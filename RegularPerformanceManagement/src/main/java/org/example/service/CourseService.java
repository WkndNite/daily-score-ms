package org.example.service;

import org.example.pojo.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {
    //添加一个课程
    void addCourse(Course course);
    //根据用户名获取一个老师的所有课程信息
    List<Course> getCourses(String username);
    //根据课程标识获取该课程平时成绩类型有哪些
    List<Map<String,String>> getUsualType(Integer courseId);
    //修改一个课程的信息
    void updateCourse(Course course);
    //删除一个课程
    void deleteCourse(Integer courseId);
}
