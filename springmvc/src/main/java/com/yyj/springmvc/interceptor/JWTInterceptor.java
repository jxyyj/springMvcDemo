package com.yyj.springmvc.interceptor;

import com.yyj.springmvc.utils.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Jwt拦截器
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 允许OPTIONS请求通过
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 获取请求路径
        String requestURI = request.getRequestURI();
        
        // 允许登录接口和静态资源访问
        if (requestURI.contains("/login") || requestURI.contains("/static") || requestURI.contains(".html")) {
            return true;
        }
        
        // 检查请求头中的token
        String token = request.getHeader("Authorization");
        
        // 如果有token，验证token
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // 移除Bearer前缀
            String username = JWTUtil.getUsernameFromToken(token);
            if (username != null && !JWTUtil.isTokenExpired(token)) {
                // token有效，将用户信息存入会话
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                return true;
            }
        }
        
        // 检查是否有登录会话
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        
        // 如果没有登录，返回401错误
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 后处理方法，可以在这里添加公共模型数据等
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 完成后处理，可以在这里进行资源清理等操作
    }
}
