package org.example.service;

import org.example.pojo.Usual;
import org.example.pojo.UsualUpdate;

import java.util.List;

public interface UsualService {
    //添加平时成绩组成部分
    void addUsual(Usual usual);
    //获取平时成绩组成部分
    List<Usual> getUsual(Integer courseId);
    //修改平时成绩组成部分
    void updateUsual(UsualUpdate usualUpdate);
    //删除平时成绩组成部分
    void deleteUsual(Integer usualId);
}
