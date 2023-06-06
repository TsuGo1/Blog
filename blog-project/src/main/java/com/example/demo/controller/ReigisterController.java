package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class ReigisterController {
	@Autowired
	private UserService accountService;

	// 显示注册屏幕。
	@GetMapping("/register")
	public String getRegisterPage() {
		return "admin_register.html";
	}
	// 注册用户信息。
	@PostMapping("/register")
	public String register(@RequestParam String userName,@RequestParam String userEmail,
			@RequestParam String password) {
		// 如果你保存该文件，你将被重定向到login.html。
		if (accountService.createAccount(userName, userEmail,password)) {
			return "redirect:/admin/login";
		} else {
			//否则，过渡到register.html
			return "admin_register.html";

		}

	}
}
