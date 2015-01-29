package com.enggameforlearn.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.util.SQLiteUtil;
import com.enggameforlearn.util.SingletonUser;

public class UserServiceImpl implements UserService{

	private SQLiteUtil sqlUitl;
	
	public UserServiceImpl(SQLiteUtil sql){
		sqlUitl = sql;
	}
	@Override
	public User login(String userName, String password) {	
		User user = sqlUitl.getUser(userName);
		if(user!=null&&user.checkPassword(password)){
			this.setLockStatus(user);
			SingletonUser.setUser(user);
		}		
		return user;
	}

	@Override
	public User register(String account,String uerName, String password) {
		if(sqlUitl.getUser(account)==null){
			ArrayList<Integer> cases = new ArrayList<>();
			cases.add(0);
			User user = new User(account);
			user.setUsername(uerName);
			user.setPassword(password);
			user.setLockCases(cases);
			sqlUitl.addUser(user);
			SingletonUser.setUser(user);
			return user;
		}else{
			return null;
		}
		
	}
	
	@Override
	public void logout(User user) {
		int size = user.getUnLockCases().size();
		int lastUnlock = user.getUnLockCases().get(size-1);
		sqlUitl.updateUnlock(user, lastUnlock);		
		sqlUitl.close();
		
	}
	

	
	/**
	 * 根据数据库信息，设置登录后该用户对关卡的解锁状态
	 */
	private void setLockStatus(User user){
		List<Integer> unLockCases = new ArrayList<Integer>();
		for(int i = 0;i<=sqlUitl.getLastUnlock(user.getAccount());i++){
			unLockCases.add(i);
		}
		user.setLockCases(unLockCases);		
	}
	@Override
	public boolean isRegister(String account) {
		return sqlUitl.getUser(account)!=null;
	}
	@Override
	public User getUser(String account) {
		return	sqlUitl.getUser(account);
		 
	}


}
