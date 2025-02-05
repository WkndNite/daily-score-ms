package org.example.service.imple;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.mapper.UsualMapper;
import org.example.pojo.Course;
import org.example.pojo.Usual;
import org.example.pojo.UsualUpdate;
import org.example.service.UsualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsualServiceImple implements UsualService {
    @Autowired
    UsualMapper usualMapper;

    @Override
    public void addUsual(Usual usual) {
        //获取课程对应的学生表的表名
        String tableName=usualMapper.getTableName(usual.getCourseId());

        //获取课程的各平时成绩组成部分的总次数
        Course course=usualMapper.getCourse(usual.getCourseId());

        //生成Student表中对应的字段名
        String usualName;
        switch (usual.getType()) {
            case "签到" -> {
                course.setSignNumber(course.getSignNumber() + 1);
                usualMapper.setUsualNumber(course);
                usualName = "sign" + course.getSignNumber();
            }
            case "作业" -> {
                course.setHomeworkNumber(course.getHomeworkNumber() + 1);
                usualMapper.setUsualNumber(course);
                usualName = "homework" + course.getHomeworkNumber();
            }
            case "实验" -> {
                course.setExperimentNumber(course.getExperimentNumber() + 1);
                usualMapper.setUsualNumber(course);
                usualName = "experiment" + course.getExperimentNumber();
            }
            default -> {
                course.setOptionalNumber(course.getOptionalNumber() + 1);
                usualMapper.setUsualNumber(course);
                usualName = "optional" + course.getOptionalNumber();
            }
        }

        usual.setName(usualName);
        //添加平时成绩组成部分
        usualMapper.addUsual(usual);

        //向对应Student表中添加对应字段
        usualMapper.addUsualName(tableName,usualName);
    }

    @Override
    public List<Usual> getUsual(Integer courseId) {
        return usualMapper.getUsual(courseId);
    }

    @Override
    public void updateUsual(UsualUpdate usualUpdate) {
        usualMapper.updateUsual(usualUpdate);
        Usual usual=usualMapper.getUsualByUsualId(usualUpdate.getUsualId());
        Course course=usualMapper.getCourse(usual.getCourseId());
        usualMapper.setStudentNewToFalse(course.getTableName());
    }

    @Override
    public void deleteUsual(Integer usualId) {
        //获取对应平时成绩组成部分信息
        Usual usual=usualMapper.getUsualByUsualId(usualId);
        //用正则表达式解析name，得到平时成绩的类型和排序
        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(usual.getName());
        String type;
        int number;
        if (matcher.find()) {
            type=matcher.group(1);
            number=Integer.parseInt(matcher.group(2));
        }else {
            throw new IllegalArgumentException("异常");
        }
        //根据课程id获取对应课程信息
        Course course=usualMapper.getCourse(usual.getCourseId());
        //根据name解析处的type查询该课程同类型的所有组成部分，按usual_id递增排列
        List<Usual> usuals=usualMapper.getSameTypeUsual(usual.getCourseId(),type);
        //删除usual表中的对应部分
        usualMapper.deleteUsual(usual.getUsualId());
        //删除student表中对应字段
        usualMapper.deleteStudentUsualName(course.getTableName(),usual.getName());
        //去掉list中删除目标和比其靠前的
        usuals.subList(0,number).clear();
        for(Usual us:usuals){
            //为list中每个重新命名包括usual中的和student中的
            String oldName=us.getName();
            us.setName(type+(Integer.parseInt(oldName.substring(type.length()))-1));
            usualMapper.setUsualName(us);
            usualMapper.setStudentUsualName(course.getTableName(),oldName,us.getName());
        }
        //更新course表中对应部分的总数量
        switch (type) {
            case "sign" -> {
                course.setSignNumber(course.getSignNumber() - 1);
                usualMapper.setUsualNumber(course);
            }
            case "homework" -> {
                course.setHomeworkNumber(course.getHomeworkNumber() - 1);
                usualMapper.setUsualNumber(course);
            }
            case "experiment" -> {
                course.setExperimentNumber(course.getExperimentNumber() - 1);
                usualMapper.setUsualNumber(course);
            }
            default -> {
                course.setOptionalNumber(course.getOptionalNumber() - 1);
                usualMapper.setUsualNumber(course);
            }
        }
    }

}
