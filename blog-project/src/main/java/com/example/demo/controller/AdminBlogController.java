package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import com.example.demo.model.entity.BlogEntity;
import com.example.demo.model.entity.CategoryEntity;
import com.example.demo.model.entity.UserEntity;
import com.example.demo.service.BlogService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;



@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {
	/**
	 * account操作
	 * Service
	 */
	@Autowired
	private UserService userService;
	/**
	 * blog操作
	 * Service
	 */
	@Autowired
	BlogService blogService;
	/**
	 * category操作
	 * Service
	 */
	@Autowired
	CategoryService categoryServie;


	/*
	 * 登录处理
	 */

	//在管理员那边显示博客的列表
	@GetMapping("/all")
	public String getLoginPage(Model model) {
		// SecurityContextHolder.getContext().getAuthentication()获得与当前请求相关的认证。
		// SecurityContextHolder.getContext() 返回与当前请求相关的SecurityContext。
		// Authentication.getAuthorities() 允许检索授予当前登录用户的权限（GrantedAuthorities的集合）。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		//获取登录者的电子邮件地址。
		String userEmail = auth.getName();
		//从ACCOUNT表中，用用户的Email进行搜索，调出相关用户的ID信息。
		UserEntity user = userService.selectById(userEmail);

		//从ACCOUNT表中检索登录用户的名字。
		String userName = user.getUserName();

		//从ACCOUNT表中获取登录用户的ID。
		Long userId = user.getUserId();

		//使用用户ID在博客表中只检索用户写的博客文章。
		List<BlogEntity>blogList = blogService.selectByUserId(userId);
		//blogList（博文列表信息）和userName（管理员的名字）在模型中被设置，并且
		//使其可以从admin_blog_all.html中引用。
		model.addAttribute("blogList",blogList);
		model.addAttribute("userName",userName);
		return "admin_blog_all.html";
	}

	//注册博文
	@GetMapping("/register")
	public String getBlogCreatePage(Model model) {
		// SecurityContextHolder.getContext().getAuthentication()获得与当前请求相关的认证。
		// SecurityContextHolder.getContext() 返回与当前请求相关的SecurityContext。
		// Authentication.getAuthorities() 允许检索授予当前登录用户的权限（GrantedAuthorities的集合）。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 获取登录者的电子邮件地址。
		String userEmail = auth.getName();
		//通过用户的Email在账户表中搜索，并调出相关用户的信息。
		UserEntity user = userService.selectById(userEmail);
		//从账户表中获取登录用户的ID。
		Long userId = user.getUserId();
		//从账户表中检索登录用户的名字
		String userName = user.getUserName();
		// 获得类别列表
		List<CategoryEntity>categoryList = categoryServie.findByAll();

		//userId (管理员的ID), categoryList (类别列表)
		//userName（管理员的名字）设置为模型，并
		//使其可以从admin_blog_register.html中引用。
		model.addAttribute("userId",userId);
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",userName);

		return "admin_blog_register.html";
	}

	// 保存注册信息。
	@PostMapping("/register")
	public String register(@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String categoryName,@RequestParam String message,@RequestParam Long userId) {

		// 获取图像文件名。
		String fileName = blogImage.getOriginalFilename();

		// 文件上传过程。
		try {
			// 指定图像文件的存储位置。
			File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
			//从图像文件中获取二进制数据
			byte[] bytes = blogImage.getBytes();
			//准备一个用于存储（导出）图像的缓冲区。
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
			//输出图像文件。
			out.write(bytes);
			//关闭缓冲区，以确保写出的内容正常结束。
			out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		//在文件上传处理后，将值传递给服务类的方法进行保存
		blogService.insert(blogTitle, fileName, categoryName, message, userId);

		return "redirect:/admin/blog/all";
	}

	/**
	 * 显示博客文章编辑页面的处理。
	 * -从链接获取blogId
	 * -找到与blogId关联的记录
	 * -设置获取到的信息以便可以从页面查看。
	 */
	//从链接标签获取blogId
	@GetMapping("/edit/{blogId}")
	public String getBlogDetailPage(@PathVariable Long blogId, Model model) {
		 //要获取与当前请求关联的Authentication，可以调用SecurityContextHolder.getContext().getAuthentication()
	    //SecurityContextHolder.getContext()返回与当前请求关联的SecurityContext。
	    //Authentication.getAuthorities()可以获取到当前登录用户赋予的权限（GrantedAuthority集合）
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//获取登录用户的电子邮件地址
	    String userEmail = auth.getName();
	    //在account表中，通过用户的Email进行搜索，然后拉出相关的用户信息。
	    UserEntity user = userService.selectById(userEmail);
	    //从account表中，获取用户名
	    String userName = user.getUserName();
	    //从account表中，获取用户ID
	    Long userId = user.getUserId();
	    //获取类别列表
	    List<CategoryEntity>categoryList = categoryServie.findByAll();
	    //在blog表中，通过blogId进行搜索，然后拉出相关的博客信息。
	    BlogEntity blogs = blogService.selectByBlogId(blogId);

	    //将userId（管理员的Id）、categoryList（类别列表）
	    //userName（管理员的名字）、blogs(与id关联的博客文章）设置到model中
	    //从admin_blog_edit.html中可以查看。
		model.addAttribute("userId",userId);
		model.addAttribute("blogs",blogs);	
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("userName",userName);
		return "admin_blog_edit.html";
	}
	/**
	 * 更新博客文章的处理。
	 * -获取图片名称，上传到blog-image。
	 * -根据输入的信息在blog表中进行更新处理。
	 * -更新处理后，将重定向到博客列表页面。
	 */
	//修改（更新）登记内容
	@PostMapping("/update")
	public String updateData(@RequestParam Long blogId,@RequestParam String blogTitle,@RequestParam("blogImage") MultipartFile blogImage,@RequestParam String categoryName,@RequestParam String message,@RequestParam Long userId) {
		//获取图片文件名
	    String fileName = blogImage.getOriginalFilename();
	    //文件上传处理
	    try {
	        //指定图片文件保存的位置。
	        File blogFile = new File("./src/main/resources/static/blog-image/"+fileName);
	        //获取图片文件的二进制数据。
	        byte[] bytes = blogImage.getBytes();
	        //为保存（写出）图片准备缓冲区。
	        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(blogFile));
	        //写出图片文件。
	        out.write(bytes);
	        //通过关闭缓冲区来正确结束写出。
	        out.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	    //在文件上传处理后，将值传递给服务类的方法进行更新。
		blogService.update(blogId, blogTitle, fileName, categoryName, message, userId);

		return "redirect:/admin/blog/all";
	}

	/**
	 * 删除博客文章的处理。
	 * -查找与blogId关联的记录
	 * -删除关联的记录
	 */
	//删除博客内容
	@PostMapping("/delete")
	public String blogDelete(@RequestParam Long blogId) {
		//将值传递给Service类，执行删除处理。
		blogService.deleteBlog(blogId);
		return "redirect:/admin/blog/all";
	}


}
