package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String showLoginForm() {
        return "login"; // 返回登录页面的视图名称
    }

    @PostMapping
    public String login() {
        // 处理登录逻辑
        return "redirect:/home"; // 重定向到登录成功后的页面
    }
}
