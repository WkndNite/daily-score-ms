package org.example.service;

import java.util.List;
import java.util.Map;

public interface StudentService {
    //根据课程标识查询对应学生表的学生信息
    List<Map<String, Object>> getStudent(Integer courseId);
    //向学生表中插入数据
    void insertStudent(List<Map<String, Object>> students,Integer courseId);
    //根据学生id删除一个学生的信息
    void deleteStudent(Integer courseId,List<String> ids);
}
