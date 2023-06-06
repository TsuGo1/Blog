package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.demo.model.entity.CategoryEntity;
import jakarta.transaction.Transactional;
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {
	// 保存类别内容。
	CategoryEntity save(CategoryEntity categoryEntity);

	//使用categoryId搜索数据库
	CategoryEntity findByCategoryId(Long categoryId);

	// 获得一个类别的列表
	List<CategoryEntity> findAll();

	//删除
	@Transactional
	List<CategoryEntity> deleteByCategoryId(Long categoryId);

}
