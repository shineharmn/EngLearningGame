package com.enggameforlearn.user.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.enggameforlearn.user.bo.User;

public interface UserService {

	User login(String userName,String password) throws HttpException, IOException;
	
	User register(String account,String uerName,String password) throws Exception;
	
	boolean isRegister(String account);
	
	void logout(User user);
	
	User getUser(String account);
	
	void updateLastUnlock(User user,int unlock) throws HttpException, IOException;
}
