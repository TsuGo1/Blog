package com.example.demo.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "user_id")
	    private Long id;

	    @Column(nullable = false, unique = true)
	    private String username;

	    @Column(nullable = false)
	    private String password;

	    // 其他用户信息字段
	 
	    // 构造函数

	    public String getPassword() {
	        return password;
	    }

		public String getUsername() {
			return null;
		}

		public void setPassword(String encryptedPassword) {

		}

	    // 其他 getter 和 setter 方法
}