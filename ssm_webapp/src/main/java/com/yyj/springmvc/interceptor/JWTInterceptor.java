package com.yyj.springmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Jwt拦截器
 * 模拟登录功能
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
public class JWTInterceptor implements HandlerInterceptor {

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @return boolean
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();
        
        // 允许登录接口和静态资源访问
        if (requestURI.contains("/login") || requestURI.contains("/static") || requestURI.contains("/error")) {
            return true;
        }
        
        // 模拟登录检查
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        
        if (user != null) {
            // 已登录，放行
            System.out.println("用户已登录，放行请求: " + requestURI);
            return true;
        } else {
            // 未登录，重定向到登录页面
            System.out.println("用户未登录，重定向到登录页面: " + requestURI);
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     *
     * @param request      请求
     * @param response     响应
     * @param handler      处理器
     * @param modelAndView 模型和视图
     * @throws Exception 异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 可以在这里对模型和视图进行处理
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理器
     * @param ex       异常
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 可以在这里进行资源清理
    }
}
