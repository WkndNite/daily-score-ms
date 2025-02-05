package org.example.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Student {
    private Integer courseId;
    private List<Map<String, Object>> students;
    private List<String> ids;
}
