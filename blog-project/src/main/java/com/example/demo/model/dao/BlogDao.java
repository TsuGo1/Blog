package com.example.demo.model.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.BlogEntity;
import jakarta.transaction.Transactional;
@Repository
public interface BlogDao extends JpaRepository<BlogEntity, Long> { 
	// 保存博客内容。
	BlogEntity save(BlogEntity blogEntity);
	// 使用博客表中的user_id和账户表中的userId来连接表，并通过userId搜索来获取数据。
	List<BlogEntity>findByUserId(Long userId);

	//使用blogId搜索数据库
	BlogEntity findByBlogId(Long blogId);
	
	// 获取博客表中的所有信息
	List<BlogEntity>findAll();
	
	// 使用类别名称搜索数据库
	List<BlogEntity>findByCategoryName(String categoryName);

	//获取blogId并删除相应的博客信息。
	@Transactional
	List<BlogEntity> deleteByBlogId(Long blogId);
}