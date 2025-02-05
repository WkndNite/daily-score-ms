package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Course;
import org.example.pojo.Usual;
import org.example.pojo.UsualUpdate;
import org.example.utils.SqlProvider;

import java.util.List;

@Mapper
public interface UsualMapper {
    //根据课程标识获取对应学生表的table_name
    @Select("select table_name from course where course_id=#{courseId}")
    String getTableName(Integer courseId);

    //根据课程标识获取课程信息
    @Select("select * " +
            "from course where course_id=#{courseId}")
    Course getCourse(Integer courseId);

    //更新课程中对应平时成绩的总次数
    @Update("update course set sign_number=#{signNumber},homework_number=#{homeworkNumber},experiment_number=#{experimentNumber},optional_number=#{optionalNumber} where course_id=#{courseId}")
    void setUsualNumber(Course course);

    //添加平时成绩组成部分
    @Insert("insert into usual(course_id,name,proportion,comment) values (#{courseId},#{name},#{proportion},#{comment})")
    void addUsual(Usual usual);

    //向对应Student表中添加字段
    @UpdateProvider(type = SqlProvider.class,method = "addUsual")
    void addUsualName(@Param("tableName") String tableName, @Param("usualName") String usualName);

    //根据课程标识获取课程的平时成绩组成部分的信息
    @Select("select * from usual where course_id=#{courseId} order by usual_id asc")
    List<Usual> getUsual(Integer courseId);

    //根据平时成绩组成部分标识修改平时成绩组成部分
    @Update("update usual set proportion=#{proportion},comment=#{comment} where usual_id=#{usualId}")
    void updateUsual(UsualUpdate usualUpdate);

    //根据平时成绩标识获取平时成绩组成部分的信息
    @Select("select * from usual where usual_id=#{usualId}")
    Usual getUsualByUsualId(Integer usualId);

    //根据课程标识和类型获取一个课程同种类型的平时成绩组成部分，并按平时成绩标识递增排列
    @Select("select * from usual where course_id=#{courseId} and name like concat(#{type},'%') order by usual_id asc")
    List<Usual> getSameTypeUsual(Integer courseId,String type);

    //为指定平时成绩组成部分重新命名
    @Update("update usual set name=#{name} where usual_id=#{usualId}")
    void setUsualName(Usual us);

    //修改对应Student表中的平时成绩组成部分的字段名
    @UpdateProvider(type = SqlProvider.class,method = "changeStudentUsualName")
    void setStudentUsualName(@Param("tableName") String tableName,@Param("oldName") String oldName,@Param("newName") String newName);

    //根据平时成绩标识删除对应部分
    @Delete("delete from usual where usual_id=#{usualId}")
    void deleteUsual(Integer usualId);

    //删除Student表中的对应平时成绩字段
    @UpdateProvider(type = SqlProvider.class,method = "deleteStudentUsualName")
    void deleteStudentUsualName(@Param("tableName") String tableName,@Param("usualName") String usualName);

    //将学生表中的new字段全部设置为false
    @Update("update ${tableName} set new=false")
    void setStudentNewToFalse(String tableName);
}
