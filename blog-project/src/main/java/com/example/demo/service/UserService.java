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
	  //ユーザの情報を保存する
	public boolean createAccount(String userName,String userEmail, String password) {
		//RegisterControllerから渡されるユーザ情報（ユーザーメールアドレス）を条件にDB検索で検索する
		UserEntity userEntity = userDao.findByUserEmail(userEmail);
		//RegisterControllerから渡されるユーザ情報（ユーザ名、パスワード）を条件にDB検索で検索した結果
		//なかった場合には、保存
		if (userEntity==null) {
			userDao.save(new UserEntity(userName,userEmail, password));
			WebSecurityConfig.addUser(userEmail, password);
			return true;
		} else {
			return false;
		}
	}
    //ユーザの一覧を取得する
	public List<UserEntity> getAllAccounts() {
		return userDao.findAll();
	}

	//idを見つけるために
	//コントローラーでわたってきたuserEmailを基にしてDBを検索
	public UserEntity selectById(String userEmail) {
		return userDao.findByUserEmail(userEmail);
	}
}