package org.example.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SqlProvider {
    //创建一个学生表
    public String createStudentTable(String tableName){
        return "create table "+tableName+" (\n" +
                "    id varchar(16) primary key ,\n" +
                "    name varchar(20) not null ,\n" +
                "    college varchar(50) ,\n" +
                "    speciality varchar(50) ,\n" +
                "    gender varchar(2) ,\n" +
                "    sign_total float ,\n" +
                "    homework_total float ,\n" +
                "    experiment_total float ,\n" +
                "    optional_total float ,\n" +
                "    usual_total float ,\n" +
                "    new boolean default false\n" +
                ")";
    }
    //给学生表中添加一个字段
    public String addUsual(String tableName,String usualName){
        return "alter table "+tableName+" add column "+usualName+" int default 0;";
    }
    //给学生表中的一个字段重命名
    public String changeStudentUsualName(String tableName,String oldName,String newName){
        return "alter table "+tableName+" change column "+oldName+" "+newName+" int default 0;";
    }
    //删除学生表中的一个字段
    public String deleteStudentUsualName(String tableName,String usualName){
        return "alter table "+tableName+" drop column "+usualName;
    }
    //删除一个学生表
    public String deleteStudentTable(String tableName){
        return "drop table "+tableName;
    }
    //为学生表创建触发器
    public String createTriggerForStudent(String tableName){
        return "create trigger before_"+tableName+"_update"+
        "   before update on "+tableName+
        "   for each row "+
        "begin"+
        "   set NEW.new=false;"+
        "end;";
    }

    //查询学生表中数据
    public String getStudent(List<String>[] names,String tableName,boolean t,boolean h){
        StringBuilder sql = new StringBuilder();
        sql.append("select id,name,college,speciality,gender");
        for(int i=0; i<4; i++){
            for(String name:names[i]){
                sql.append(",").append(name);
            }
        }

        if(t){
            if(!names[0].isEmpty()){
                sql.append(",sign_total as signTotal");
            }
            if(!names[1].isEmpty()){
                sql.append(",homework_total as homeworkTotal");
            }
            if(!names[2].isEmpty()){
                sql.append(",experiment_total as experimentTotal");
            }
            if(!names[3].isEmpty()){
                sql.append(",optional_total as optionalTotal");
            }
            sql.append(",usual_total as usualTotal");
        }
        sql.append(" from ").append(tableName);

        if(h){
            sql.append(" where new=false");
        }

        return sql.toString();
    }

    //向学生表中插入数据
    public String insertStudentTable(Map<String, Object> student, StringBuilder insertSQL, Set<String> names){
        insertSQL.append("\"").append(student.get("id").toString()).append("\"");
        insertSQL.append(",\"").append(student.get("name").toString()).append("\"");
        for (String name : names){
            Object value = student.get(name);
            if(value instanceof String){
                insertSQL.append(",\"").append(value).append("\"");
            }else{
                if(value==null){
                    insertSQL.append(",").append(0);
                }else{
                    insertSQL.append(",").append(value);
                }
            }
        }
        insertSQL.append(")");

        return insertSQL.toString();
    }

    //修改学生表中的数据
    public String updateStudentTable(Map<String, Object> student, StringBuilder updateSQL, Set<String> names){
        updateSQL.append("name=").append("\"").append(student.get("name").toString()).append("\"");
        for (String name : names){
            Object value = student.get(name);
            if(value instanceof String){
                updateSQL.append(",").append(name).append("=\"").append(value).append("\"");
            }else {
                if(value==null){
                    updateSQL.append(",").append(name).append("=").append(0);
                }else {
                    updateSQL.append(",").append(name).append("=").append(value);
                }
            }
        }
        updateSQL.append(" where id=").append("\"").append(student.get("id").toString()).append("\"");

        return updateSQL.toString();
    }
}
