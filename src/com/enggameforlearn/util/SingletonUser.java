package com.enggameforlearn.util;

import com.enggameforlearn.user.bo.User;

public class SingletonUser {

	private static User user = new User("");
	
	public static synchronized void setUser(User u){
		user.setAccount(u.getAccount());
		user.setLockCases(u.getUnLockCases());
		user.setPassword(u.getPassword());
		
	}
	
	public static User getUser(){
		return user;
	}
}
