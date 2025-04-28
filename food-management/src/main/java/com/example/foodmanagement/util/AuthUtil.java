package com.example.foodmanagement.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.foodmanagement.model.entity.Users;
import com.example.foodmanagement.repository.UsersRepository;

@Component
public class AuthUtil {
	@Autowired
	UsersRepository usersRepository;
	
	//ログインユーザ
	public Users getLoginUser(Authentication auth) {
		return  usersRepository.findByUsername(auth.getName()).orElseThrow();
	}
}
