package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm() {
        return "register"; // 返回注册页面的视图名称
    }

    @PostMapping
    public String register(User user, String confirmPassword, Model model) {
        // 验证密码和确认密码是否匹配
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "密码和确认密码不匹配");
            return "register"; // 返回注册页面的视图名称
        }

        // 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在");
            return "register"; // 返回注册页面的视图名称
        }

        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 保存用户到数据库
        userRepository.save(user);

        return "redirect:/login"; // 重定向到登录页面
    }
}
