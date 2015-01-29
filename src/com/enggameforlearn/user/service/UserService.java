package com.enggameforlearn.user.service;

import com.enggameforlearn.user.bo.User;

public interface UserService {

	User login(String userName,String password);
	
	User register(String account,String uerName,String password);
	
	boolean isRegister(String account);
	
	void logout(User user);
	
	User getUser(String account);
}
