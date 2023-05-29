package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.demo.model.entity.CategoryEntity;
import jakarta.transaction.Transactional;
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {
	//カテゴリー内容の保存
	CategoryEntity save(CategoryEntity categoryEntity);

	//categoryIdを使用してDBに検索をかける
	CategoryEntity findByCategoryId(Long categoryId);

	//カテゴリー一覧を取得
	List<CategoryEntity> findAll();

	//削除
	@Transactional
	List<CategoryEntity> deleteByCategoryId(Long categoryId);

}
