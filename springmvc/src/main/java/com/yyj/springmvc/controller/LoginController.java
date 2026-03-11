package com.yyj.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 *
 * @author yan yinjie
 * @date 2026/03/11
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, 
                       @RequestParam("password") String password, 
                       HttpSession session) {
        // 简单的登录验证（实际项目中应该从数据库验证）
        if ("admin".equals(username) && "admin".equals(password)) {
            // 登录成功，将用户信息存入会话
            session.setAttribute("user", username);
            return "redirect:/index.html";
        } else {
            // 登录失败，重定向到登录页面
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除会话中的用户信息
        session.invalidate();
        return "redirect:/login";
    }
}