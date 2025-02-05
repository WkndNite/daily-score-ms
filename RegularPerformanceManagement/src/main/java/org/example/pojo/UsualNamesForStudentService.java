package org.example.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UsualNamesForStudentService {
    List<String>[] usualNames;
    Map<String, Integer> nameAndProportion;
    int[] proportions;
}
