package com.yyj.springmvc.controller;

import com.yyj.springmvc.common.Result;
import com.yyj.springmvc.entity.LoginRequest;
import com.yyj.springmvc.utils.JWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录控制器
 *
 * @author yan yinjie
 * @date 2026/03/11
 */
@RestController
public class LoginController {

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login.html";
//    }

    @PostMapping("/login")
    public String login (@RequestBody LoginRequest loginRequest,
                       HttpSession session) {
        // 简单的登录验证（实际项目中应该从数据库验证）
        if ("admin".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            // 登录成功，生成token
            String token = JWTUtil.generateToken(loginRequest.getUsername());
            // 将用户信息存入会话
            session.setAttribute("user", loginRequest.getUsername());
            return token;
        } else {
            // 登录失败
            return "用户名或密码错误";
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public Result logout(HttpSession session) {
        // 清除会话中的用户信息
        session.invalidate();
        return Result.success("登出成功");
    }
}