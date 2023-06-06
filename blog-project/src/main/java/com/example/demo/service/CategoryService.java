package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.CategoryDao;
import com.example.demo.model.entity.CategoryEntity;

@Service
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;

	// 列表显示。
	public List<CategoryEntity> findByAll() {
		return categoryDao.findAll();
	}

	// 保存内容。
	public void insert(String categoryName) {
		categoryDao.save(new CategoryEntity(categoryName));
	}

	// 编辑。
	public void update(Long categoryId,String categoryName) {
		categoryDao.save(new CategoryEntity(categoryId,categoryName));
	}
	
	//CategoryID被用来作为DB中搜索的基础。
	public CategoryEntity selectCategoryId(Long categoryId) {
		return categoryDao.findByCategoryId(categoryId);
	}

	//删除
	public void delete(Long categoryId) {
		categoryDao.deleteById(categoryId);
	}
}
