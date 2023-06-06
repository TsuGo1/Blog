package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginController {
	// 显示登录界面。
		@GetMapping("/login")
		public String getLoginPage() {
			return "admin_login.html";
		}
}
