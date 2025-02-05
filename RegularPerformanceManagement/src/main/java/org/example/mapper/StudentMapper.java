package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.utils.SqlProvider;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface StudentMapper {
    //根据表名查询出学生表中的数据
    @SelectProvider(type = SqlProvider.class,method = "getStudent")
    List<Map<String, Object>> getStudent(@Param("names") List<String>[] names, @Param("tableName") String tableName,@Param("t") boolean t,@Param("h") boolean h);

    //根据表名查询出表的字段名
    @Select("select column_name from INFORMATION_SCHEMA.COLUMNS where table_name=#{tableName}")
    List<String> getColumnName(String tableName);

    //根据学生表名获取所有学号
    @Select("select id from ${tableName}")
    List<String> getStudentId(String tableName);

    void insertStudentByXML(@Param("student") Map<String, Object> student, @Param("tableName") String tableName, @Param("names") Set<String> names);
    //向学生表中插入数据
    @UpdateProvider(type = SqlProvider.class,method = "insertStudentTable")
    void insertStudent(@Param("student") Map<String, Object> student,@Param("insertSQL") StringBuilder insertSQL,@Param("names") Set<String> names);

    //修改学生表中的数据
    @UpdateProvider(type = SqlProvider.class,method = "updateStudentTable")
    void updateStudent(@Param("student") Map<String, Object> student,@Param("updateSQL") StringBuilder updateSQL,@Param("names") Set<String> names);

    //根据学生学号删除学生信息
    @Delete("delete from ${tableName} where id=#{id}")
    void deleteStudent(String tableName,String id);
}
