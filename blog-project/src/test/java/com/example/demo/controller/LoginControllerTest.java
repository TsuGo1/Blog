package com.example.demo.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	@Autowired
	private MockMvc mockMvc;


	@Test
	// 登录页面显示正确。
	public void AccessLoginPage() throws Exception{

		RequestBuilder request = MockMvcRequestBuilders//
				.get("/admin/login");

		mockMvc.perform(request)//
		// login.html应该作为一个响应被返回
		.andExpect(view().name("admin_login.html"));

	}
	@Test
	// 如果登录成功，用户将被重定向到博客列表页面。
	public void testLogin_CorrectInfo_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/admin/login")
				//接受用户的名字和用户的密码作为参数。
				.param("userEmail", "alice@test.com")
				.param("password", "Alice123456")
		        .with(csrf());

		mockMvc.perform(request)//
		.andExpect(redirectedUrl("/admin/blog/all"));
	}


}