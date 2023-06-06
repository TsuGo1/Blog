package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.entity.BlogEntity;
import com.example.demo.model.entity.CategoryEntity;
import com.example.demo.service.BlogService;
import com.example.demo.service.CategoryService;


@Controller
@RequestMapping("/blog")
public class UserBlogController {
	/**
	 * 要操作博客表。
	 *Service类。
	 */
	@Autowired
	BlogService blogService;
	/**
	 *操作类别表。
	 *Service类。
	 */
	@Autowired
	CategoryService categoryServie;

	/**
	 *从博客表中获取所有的博客文章
	 *从类别表中检索所有的类别。
	 *设置检索到的信息并使其在屏幕上可供参考。
	 */
	// 显示博客屏幕（无登录操作）。
	@GetMapping("/")
	public String getBlogUserPage(Model model) {
		//获取博客上的所有信息。
		List<BlogEntity>blogList = blogService.selectByAll();
		model.addAttribute("blogList",blogList);
		//获取该类别的所有信息。
		List<CategoryEntity>categoryList = categoryServie.findByAll();
		model.addAttribute("categoryList",categoryList);
		return "blog.html";
	}
	/**
	 * 显示博客文章详情屏幕的过程。
	 * 从博客表中获取与blogId相关的内容。
	 * 从类别表中获取所有类别。
	 * 设置获得的信息，并使其可用于屏幕上的参考。
	 */
	@GetMapping("/detail/{blogId}")
	public String getBlogUserDetailPage(@PathVariable Long blogId, Model model) {
		//在博客表中通过blogId进行搜索，拉出相关的相关博客的信息。
		BlogEntity blogs = blogService.selectByBlogId(blogId);
		// 获取该类别的所有信息。
		List<CategoryEntity>categoryList = categoryServie.findByAll();
		//blogs（详细的博客文章信息）和categoryList（类别列表）被设置在模型和
		//blog_detail.html中，以使它们可供参考。
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("blogs",blogs);	
		return "blog_detail.html";
	}
	/**
	 * 按类别显示博客文章的处理。
	 *从博客表中检索与categoryName相关的内容
	 *从类别表中检索所有的类别
	 * 设置获取的信息，并使其可在屏幕上参考。
	 */
	@GetMapping("/category/list/{categoryName}")
	public String getBlogCategoryAllPage(@PathVariable String categoryName, Model model) {
		//通过搜索categoryName，从blog表中提取相关的相关博客的信息。
		List<BlogEntity> blogList = blogService.selectByCategoryName(categoryName);
		//获取该类别的所有信息。
		List<CategoryEntity>categoryList = categoryServie.findByAll();
		model.addAttribute("categoryList",categoryList);
		model.addAttribute("categoryName",categoryName);
		model.addAttribute("blogList",blogList);	
		//将blogList（博客文章列表）、categoryList（类别列表）和categoryName（类别名称）设置为模型，并且
		//使它们可以从blog_category_list.html中引用。
		return "blog_category_list.html";
	}
}
