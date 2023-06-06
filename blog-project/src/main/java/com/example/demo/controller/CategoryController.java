package com.example.demo.controller;

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

import com.example.demo.model.entity.CategoryEntity;
import com.example.demo.model.entity.UserEntity;
import com.example.demo.service.CategoryService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

	/**
     * 用于操作category表的Service类
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * 用于操作account表的Service类
     */
    @Autowired
    private UserService userService;

    /**
     * 显示category列表页面的处理。
     * - 使用当前登录用户的电子邮件获取当前登录用户的userId和userName。
     * - 获取category列表。
     * - 将获取的信息设置到模型中，以便页面可以引用。
     */
	//分类列表表示
	@GetMapping("/all")
	public String getCategoryAll(Model model) {
		// SecurityContextHolder.getContext().getAuthentication()获得与当前请求相关的认证。
		// SecurityContextHolder.getContext() 返回与当前请求相关的SecurityContext。
		// Authentication.getAuthorities() 允许检索授予当前登录用户的权限（GrantedAuthorities的集合）。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// 获取登录者的电子邮件地址。
		String userEmail = auth.getName();
		//通过用户的Email在account中搜索，并调出相应用户的ID信息。
		UserEntity user = userService.selectById(userEmail);

		//从account中检索登录用户的名字
		String userName = user.getUserName();
		//获取category中的所有内容，并将其存储在categorylist中。
		List<CategoryEntity>categorylist = categoryService.findByAll();

		//在模型中设置categorylist（类别列表信息）和userName（管理员的名字），并且
		//将categorylist（类别列表信息）和userName（管理员的名字）设置在模型中，并使之可从admin_category_all.html中获得参考。
		model.addAttribute("categorylist",categorylist);
		model.addAttribute("userName",userName);

		return "admin_category_all.html";
	}
	/**
	 *显示屏幕保存类别内容的过程。
	 * -获取登录者的userId和userName，使用登录者的电子邮件地址。
	 * -设置检索到的信息，并使其可在屏幕上参考。
	 */
	//显示类别注册画面。
	@GetMapping("/register")
	public String geCategoryListRegister(Model model) {
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

		//将userName（管理员的名字）设置为模型，并且
	    //使其可以从admin_category_register.html中引用。
		model.addAttribute("userName",userName);
		return "admin_category_register.html";
	}

	/**
	 * -存储类别内容的过程。
	 * -根据输入的信息保存分类表的过程。
	 * -使用检索到的userId获取登录者的博客文章。
	 * -保存过程结束后，用户被重定向到类别列表界面。
	 */
	// 保存类别的注册信息。
	@PostMapping("/register")
	public String register(@RequestParam String categoryName) {
		// 向服务类的方法传递和存储数值。
		categoryService.insert(categoryName);
		return "redirect:/admin/category/all";
	}

	/**
	 * 显示类别编辑屏幕的过程。
	 * -使用登录者的电子邮件地址，获取登录者的userId和userName。
	 * -从链接中获取categoryId。
	 * -找到与categoryId相关的记录。
	 * -设置检索到的信息，并使其可从屏幕上引用。
	 */
	// 显示类别编辑界面。
	@GetMapping("/edit/{categoryId}")
	public String getCategroyEditPage(@PathVariable Long categoryId,Model model) {
		// SecurityContextHolder.getContext().getAuthentication()获得与当前请求相关的认证。
		// SecurityContextHolder.getContext() 返回与当前请求相关的SecurityContext。
		// Authentication.getAuthorities() 允许检索授予当前登录用户的权限（GrantedAuthorities的集合）。
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// 获取登录者的电子邮件地址。
		String userEmail = auth.getName();

		//通过用户的Email在account表中搜索，并调出相应用户的ID信息。
		UserEntity user = userService.selectById(userEmail);

		//从account表中检索登录用户的名字
		String userName = user.getUserName();

		//通过搜索category_id，从category表中拉出相关的类别信息。
		CategoryEntity categoryEntity = categoryService.selectCategoryId(categoryId);

		//在模型中设置categoryEntity（类别信息）和userName（管理员的名字），并且
		//使之有可能从admin_category_edit.html中引用它。
		model.addAttribute("userName",userName);
		model.addAttribute("category",categoryEntity);
		return "admin_category_edit.html";
	}

	/**
	 * -保存编辑到类别的过程。
	 * -从屏幕接收信息并更新分类表的过程。
	 * -从链接中获取categoryId。
	 * -更新过程结束后，重定向到类别列表屏幕。
	 */
	@PostMapping("/update")
	public String update(@RequestParam String categoryName,@RequestParam Long categoryId) {
		//CategoryServiceのメソッドに値を渡して更新を行う。
		categoryService.update(categoryId, categoryName);
		return "redirect:/admin/category/all";
	}

	/**
	 * 删除类别的过程。
	 * 找到与categoryId绑定的记录。
	 * -删除与之相关的记录。
	 */
	// 删除类别注册信息。
	@PostMapping("/delete")
	public String deleteCategory(@RequestParam Long categoryId) {
		//将该值发送给Service并执行删除过程。
		categoryService.delete(categoryId);
		return "redirect:/admin/category/all";
	}


}
