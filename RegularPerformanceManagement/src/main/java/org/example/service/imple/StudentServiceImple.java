package org.example.service.imple;

import org.example.mapper.StudentMapper;
import org.example.mapper.UsualMapper;
import org.example.pojo.Course;
import org.example.pojo.Usual;
import org.example.pojo.UsualNamesForStudentService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentServiceImple implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private UsualMapper usualMapper;

    @Override
    public List<Map<String, Object>> getStudent(Integer courseId) {
        String tableName = usualMapper.getTableName(courseId);

        //检验课程标识是否存在
        if (tableName == null) {
            throw new IllegalArgumentException("课程不存在");
        }
        List<Map<String, Object>> students;

        //获取课程的平时成绩相关数据
        UsualNamesForStudentService usualNamesForStudentService=getUsualNames(courseId);
        List<String>[] usualNames= usualNamesForStudentService.getUsualNames();
        int[] proportions=usualNamesForStudentService.getProportions();

        boolean t=true;
        //判断各种类型平时成绩百分比和是否为100
        for(int i=0;i<3;i++){
            if (proportions[i] != 0 && proportions[i] != 100) {
                t = false;
                break;
            }
        }

        if(t){//百分比和没问题
            //计算总成绩
            Map<String,Integer> nameAndProportion=usualNamesForStudentService.getNameAndProportion();
            calculateUsualScore(usualNames,tableName,courseId,nameAndProportion);
            //查询数据
            students=studentMapper.getStudent(usualNames,tableName,t,false);
        }else {//百分比和有问题
            //查询数据
            students=studentMapper.getStudent(usualNames,tableName,t,false);
        }

        //判断数据是否为空，为空的话返回字段名
        if (students.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", null);
            map.put("name", null);
            map.put("college", null);
            map.put("speciality", null);
            map.put("gender", null);
            for(int i=0;i<4;i++){
                for (String name: usualNames[i]) {
                    map.put(name, null);
                }
            }
            if(t){
                if(!usualNames[0].isEmpty()){
                    map.put("signTotal", null);
                }
                if(!usualNames[1].isEmpty()){
                    map.put("homeworkTotal", null);
                }
                if(!usualNames[2].isEmpty()){
                    map.put("experimentTotal", null);
                }
                if(!usualNames[3].isEmpty()){
                    map.put("optionalTotal", null);
                }
                map.put("usualTotal", null);
            }
            students.add(map);
        }

        return students;
    }

    //解析平时成绩
    private UsualNamesForStudentService getUsualNames(Integer courseId){
        List<Usual> usuals=usualMapper.getUsual(courseId);
        //用正则表达式解析name，得到平时成绩的类型和比例
        Pattern pattern = Pattern.compile("([a-zA-Z]+)(\\d+)");

        List<String>[] usualNames =new List[4];//存储每种类型平时成绩的各部分名称的集合
        usualNames[0]=new ArrayList<>();
        usualNames[1]=new ArrayList<>();
        usualNames[2]=new ArrayList<>();
        usualNames[3]=new ArrayList<>();
        Map<String, Integer> nameAndProportion=new HashMap<>();
        int[] proportions =new int[4];//存储每种类型平时成绩的各部分占比和

        for(Usual usual:usuals){
            nameAndProportion.put(usual.getName(),usual.getProportion());
            Matcher matcher = pattern.matcher(usual.getName());
            String type;
            if (matcher.find()) {
                type=matcher.group(1);
            }else {
                throw new IllegalArgumentException("异常");
            }
            switch (type){
                case "sign" -> {
                    usualNames[0].add(usual.getName());
                    proportions[0]+=usual.getProportion();
                }
                case "homework" -> {
                    usualNames[1].add(usual.getName());
                    proportions[1]+=usual.getProportion();
                }
                case "experiment" -> {
                    usualNames[2].add(usual.getName());
                    proportions[2]+=usual.getProportion();
                }
                case "optional" -> {
                    usualNames[3].add(usual.getName());
                    proportions[3]+=usual.getProportion();
                }
            }
        }

        UsualNamesForStudentService usualNamesForStudentService=new UsualNamesForStudentService();
        usualNamesForStudentService.setUsualNames(usualNames);
        usualNamesForStudentService.setNameAndProportion(nameAndProportion);
        usualNamesForStudentService.setProportions(proportions);

        return usualNamesForStudentService;
    }

    //计算平时成绩
    private void calculateUsualScore(List<String>[] usualNames, String tableName, Integer courseId, Map<String, Integer> nameAndProportion){
        Course course=usualMapper.getCourse(courseId);
        //获取更新过后没有计算过的数据
        List<Map<String,Object>> students=studentMapper.getStudent(usualNames,tableName,false,true);
        if(students.isEmpty()){
            return;
        }
        for(Map<String,Object> student:students){
            float usualTotal=0;
            for(int i=0;i<4;i++){
                if(!usualNames[i].isEmpty()){
                    int totalTemp=0;
                    for(String name: usualNames[i]){
                        totalTemp+=(Integer)student.get(name)*nameAndProportion.get(name);
                    }
                    switch (i){
                        case 0 -> {
                            float temp=(float)totalTemp/100;
                            usualTotal+=temp*course.getSign();
                            student.put("sign_total",temp);
                        }
                        case 1 -> {
                            float temp=(float)totalTemp/100;
                            usualTotal+=temp*course.getHomework();
                            student.put("homework_total",temp);
                        }
                        case 2 -> {
                            float temp=(float)totalTemp/100;
                            usualTotal+=temp*course.getExperiment();
                            student.put("experiment_total",temp);
                        }
                        case 3 -> {
                            float temp=(float)totalTemp/100;
                            student.put("optional_total",temp);

                            usualTotal+=temp*course.getOptional();
                            if(usualTotal>10000){
                                usualTotal=10000;
                            }
                        }
                    }
                }
                student.put("usual_total",usualTotal/10000*course.getUsual());
            }
            student.put("new",true);
        }

        //将计算过后的数据写回数据库
        Set<String> namesSet = new HashSet<>(students.get(0).keySet());//获取数据中的全部字段名
        namesSet.remove("id");
        namesSet.remove("name");
        Set<String> names = new LinkedHashSet<>(namesSet);
        //构建sql修改语句
        String updateTemp= "update " + tableName + " set ";
        for(Map<String, Object> student:students){
            studentMapper.updateStudent(student,new StringBuilder(updateTemp),names);
        }
    }

    @Override
    public void insertStudent(List<Map<String, Object>> students, Integer courseId) {
        String tableName = usualMapper.getTableName(courseId);
        //检验课程标识是否存在
        if (tableName == null) {
            throw new IllegalArgumentException("课程不存在");
        }

        //获取数据中的全部字段名
        Set<String> namesSet = new HashSet<>(students.get(0).keySet());
        namesSet.remove("id");
        namesSet.remove("name");
        Set<String> names = new LinkedHashSet<>(namesSet);
        //构建sql插入语句
        StringBuilder insertSQL= new StringBuilder("insert into " + tableName + "(id,name");
        for (String name : names) {
            insertSQL.append(",").append(name);
        }
        insertSQL.append(") values(");
        String insertTemp=insertSQL.toString();
        //构建sql修改语句
        String updateTemp= "update " + tableName + " set ";

        //获取学生表中的所有学号
        List<String> ids=studentMapper.getStudentId(tableName);
        for(Map<String, Object> student:students){
            String id=(String)student.get("id");
            if(ids.contains(id)){ //判断对数据进行插入还是修改
                System.out.println("yi");
                studentMapper.updateStudent(student,new StringBuilder(updateTemp),names);
            }else{
                System.out.println("er");
                studentMapper.insertStudent(student,new StringBuilder(insertTemp),names);
                //studentMapper.insertStudentByXML(student,tableName,names);
            }
        }

        //获取课程的平时成绩相关数据
        UsualNamesForStudentService usualNamesForStudentService=getUsualNames(courseId);
        List<String>[] usualNames= usualNamesForStudentService.getUsualNames();
        int[] proportions=usualNamesForStudentService.getProportions();

        boolean t=true;
        //判断各种类型平时成绩百分比和是否为100
        for(int i=0;i<3;i++){
            if (proportions[i] != 0 && proportions[i] != 100) {
                t = false;
                break;
            }
        }

        if(t) {//百分比和没问题
            //计算总成绩
            Map<String, Integer> nameAndProportion = usualNamesForStudentService.getNameAndProportion();
            calculateUsualScore(usualNames, tableName, courseId, nameAndProportion);
        }
    }

    @Override
    public void deleteStudent(Integer courseId,List<String> ids) {
        String tableName = usualMapper.getTableName(courseId);
        for (String id : ids) {
            studentMapper.deleteStudent(tableName,id);
        }
    }
}
