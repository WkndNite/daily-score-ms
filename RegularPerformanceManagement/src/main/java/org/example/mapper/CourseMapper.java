package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Course;
import org.example.utils.SqlProvider;

import java.util.List;

@Mapper
public interface CourseMapper {
    //添加一个课程并返回课程标识
    @Insert("insert into course(username,course_name,school,college,term,teacher_name,usual,sign,homework,experiment,optional,sign_number,homework_number,experiment_number,optional_number)" +
            " values(#{username},#{courseName},#{school},#{college},#{term},#{teacherName},#{usual},#{sign},#{homework},#{experiment},#{optional},#{signNumber},#{homeworkNumber},#{experimentNumber},#{optionalNumber})" )
    //使用Options注解将生成的主键值再赋给course对象中的courseId，方便Service层重新查询table_name
    @Options(useGeneratedKeys = true, keyProperty = "courseId", keyColumn = "course_id")
    void addCourse(Course course);

    //设置课程对应的学生表的表名
    @Update("update course set table_name=#{tableName} where course_id=#{courseId}")
    void setTableName(Course course);

    //创建一个课程表对应的学生表
    @UpdateProvider(type = SqlProvider.class,method = "createStudentTable")
    void createStudentTable(@Param("tableName") String tableName);

    //根据用户名获取一个老师的所有课程信息
    @Select("select * from course where username=#{username}")
    List<Course> getCourses(String username);

    //根据课程标识获取一个课程的信息
    @Select("select * from course where course_id=#{courseId}")
    Course getCourseByCourseId(Integer courseId);

    //根据课程标识修改一个课程的信息
    @Update("update course set course_name=#{courseName},school=#{school},college=#{college},term=#{term},teacher_name=#{teacherName},usual=#{usual},sign=#{sign},homework=#{homework},experiment=#{experiment},optional=#{experiment} where course_id=#{courseId}")
    void updateCourse(Course course);

    //根据课程标识删除一个课程
    @Delete("delete from course where course_id=#{courseId}")
    void deleteCourse(Integer courseId);

    //根据stuent表名删除一个student表
    @UpdateProvider(type = SqlProvider.class,method = "deleteStudentTable")
    void deleteStudentTable(@Param("tableName") String tableName);

    //为学生表创建触发器
    @UpdateProvider(type = SqlProvider.class,method = "createTriggerForStudent")
    void createTriggerForStudent(@Param("tableName") String tableName);
}
