package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.WebSecurityConfig;
import com.example.demo.model.dao.UserDao;
import com.example.demo.model.entity.UserEntity;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	//存储用户信息。
	public boolean createAccount(String userName,String userEmail, String password) {
		//RegisterController使用RegisterController传来的用户信息（用户电子邮件地址）作为条件，在DB搜索中进行搜索。
		UserEntity userEntity = userDao.findByUserEmail(userEmail);
		//作为DB搜索的结果，使用从RegisterController传来的用户信息（用户名，密码）作为一个条件
		//如果没有找到，保存
		if (userEntity==null) {
			userDao.save(new UserEntity(userName,userEmail, password));
			WebSecurityConfig.addUser(userEmail, password);
			return true;
		} else {
			return false;
		}
	}
	// 获得一个用户列表
	public List<UserEntity> getAllAccounts() {
		return userDao.findAll();
	}

	// 找到id
	//根据控制器中出现的userEmail搜索数据库。
	public UserEntity selectById(String userEmail) {
		return userDao.findByUserEmail(userEmail);
	}
}