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

	//内容を保存
		public void insert(String blogTitle,String fileName,String categoryName,String message,Long userId) {
			blogDao.save(new BlogEntity(blogTitle,fileName,categoryName,message,userId));
		}
	//ブログ一覧
	public List<BlogEntity> selectByUserId(Long userId){
		return blogDao.findByUserId(userId);
	}

	//ブログ詳細
	public BlogEntity selectByBlogId(Long blogId){
		return blogDao.findByBlogId(blogId);
	}
	//内容を更新
	public void update(Long blogId,String blogTitle,String fileName,String categoryName,String message,Long userId) {
		blogDao.save(new BlogEntity(blogId,blogTitle,fileName,categoryName,message,userId));
	}

	//ユーザーブログ一覧
	public List<BlogEntity> selectByAll(){
		return blogDao.findAll();
	}

	//カテゴリー一事の内容
	public List<BlogEntity> selectByCategoryName(String categoryName){
		return blogDao.findByCategoryName(categoryName);
	}
	//削除
	public List<BlogEntity>deleteBlog(Long blogId){
		return blogDao.deleteByBlogId(blogId);
	}

}
