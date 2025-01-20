package org.example;

import java.security.SecureRandom;

public class experiment {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        // 生成一个6位数，并使用格式化字符串确保长度
        String s=String.format("%06d", secureRandom.nextInt(1000000));
        System.out.println(s);
    }
}
