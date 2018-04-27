package com.ks.service;

import com.ks.DAO.UserDAO;
import com.ks.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

	public Boolean updateUser(User user){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		//String hashedPassword = encoder.encode(user.getPassword());
		user.setPassword(encoder.encode(user.getPassword()));
		return userDAO.addOrUpdateUser(user);
	}
}
