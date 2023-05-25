package com.example.demo.service;
import com.example.demo.model.Blog;

import java.util.List;
public interface BlogService {
    Blog createBlog(Blog blog);
    List<Blog> getAllBlogs();
    Blog getBlogById(Long id);
    Blog updateBlog(Blog blog);
    void deleteBlog(Long id);
}