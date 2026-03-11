package com.yyj.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 * 模拟登录功能
 *
 * @author yan yinjie
 * @date 2026/03/10
 */
@Controller
public class LoginController {

    /**
     * 跳转到登录页面
     *
     * @return ModelAndView
     */
    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    /**
     * 登录处理
     *
     * @param username 用户名
     * @param password 密码
     * @param session  session
     * @return ModelAndView
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // 模拟登录验证
        if ("admin".equals(username) && "123456".equals(password)) {
            // 登录成功，设置session
            session.setAttribute("user", username);
            System.out.println("登录成功: " + username);
            // 重定向到学生列表页
            return new ModelAndView("redirect:/student/getAll");
        } else {
            // 登录失败，返回登录页面
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", "用户名或密码错误");
            System.out.println("登录失败: " + username);
            return modelAndView;
        }
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return ModelAndView
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        // 清除session
        session.invalidate();
        System.out.println("退出登录");
        // 重定向到登录页面
        return new ModelAndView("redirect:/login");
    }
}
