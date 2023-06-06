package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.dao.BlogDao;
import com.example.demo.model.entity.BlogEntity;

@Service
public class BlogService {
	@Autowired
	BlogDao blogDao;

	// 保存内容。
		public void insert(String blogTitle,String fileName,String categoryName,String message,Long userId) {
			blogDao.save(new BlogEntity(blogTitle,fileName,categoryName,message,userId));
		}
	// 保存内容。
	public List<BlogEntity> selectByUserId(Long userId){
		return blogDao.findByUserId(userId);
	}

	//博客内容
	public BlogEntity selectByBlogId(Long blogId){
		return blogDao.findByBlogId(blogId);
	}
	//内容更新
	public void update(Long blogId,String blogTitle,String fileName,String categoryName,String message,Long userId) {
		blogDao.save(new BlogEntity(blogId,blogTitle,fileName,categoryName,message,userId));
	}

	//用户博客列表
	public List<BlogEntity> selectByAll(){
		return blogDao.findAll();
	}

	//类型内容
	public List<BlogEntity> selectByCategoryName(String categoryName){
		return blogDao.findByCategoryName(categoryName);
	}
	//删除
	public List<BlogEntity>deleteBlog(Long blogId){
		return blogDao.deleteByBlogId(blogId);
	}

}
