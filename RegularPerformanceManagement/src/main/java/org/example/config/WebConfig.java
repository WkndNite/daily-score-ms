package org.example.config;

import org.example.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器，并开放注册和登录接口
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/loginAccount","/user/loginPhone","/user/register","/user/code");
    }
}
