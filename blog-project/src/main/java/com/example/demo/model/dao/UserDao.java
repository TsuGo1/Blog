package com.example.demo.model.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {
	
	// 根据从UserService传来的用户信息（电子邮件地址）进行DB搜索
	UserEntity findByUserEmail(String userEmail);

	// 根据从UserService传来的用户信息存储在数据库中
	UserEntity save(UserEntity userEntity);

	// 获得一个用户信息的列表。
	List<UserEntity> findAll();
}