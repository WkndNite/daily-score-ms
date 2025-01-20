package org.example.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.utils.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//构建一个拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        //获取令牌
        String token=request.getHeader(("token"));
        //令牌验证
        try{
            Map<String,Object> map= JwtUtil.parseToken(token);
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
