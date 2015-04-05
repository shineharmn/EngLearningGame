package com.enggameforlearn.user.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import net.sf.json.JSONObject;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.util.SQLiteUtil;
import com.enggameforlearn.util.SingletonUser;
import com.enggameforlearn.web.CasesConnection;
import com.enggameforlearn.web.LoginConnection;
import com.enggameforlearn.web.RegisterConnection;

public class UserServiceImpl implements UserService{

	private SQLiteUtil sqlUitl;
	
	public UserServiceImpl(SQLiteUtil sql){
		sqlUitl = sql;
	}
	@Override
	public User login(String userName, String password) throws HttpException, IOException {	
//		User user = sqlUitl.getUser(userName);
		String jsonstr = LoginConnection.login(userName, password);
		if(!"".equals(jsonstr)){
			JSONObject json = JSONObject.fromObject(jsonstr);
			String nickname = json.getString("nickname");
			int lastUnlock = Integer.parseInt(json.getString("lastUnlock"));
			User user = new User(userName);
			user.setNickname(nickname);
			user.setPassword(password);
			if(sqlUitl.getUser(userName)==null){
				sqlUitl.addUser(user);
			}
			setLockStatus(user,lastUnlock);
			SingletonUser.setUser(user);
			return user;
		}
//		if(user!=null&&user.checkPassword(password)){
//			this.setLockStatus(user);
//			SingletonUser.setUser(user);
//		}		
		return null;
	}

	@Override
	public User register(String account,String uerName, String password) throws Exception {
		User user = new User(account);
		user.setNickname(uerName);
		user.setPassword(password);
		String json = JSONObject.fromObject(user).toString();
		if(RegisterConnection.canRegister(json)){
			ArrayList<Integer> cases = new ArrayList<Integer>();
			cases.add(1);			
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
	private void setLockStatus(User user,int lastUnlock){
		List<Integer> unLockCases = new ArrayList<Integer>();
		for(int i = 1;i<=lastUnlock;i++){
			unLockCases.add(i);
		}
		sqlUitl.updateUnlock(user, lastUnlock);
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
	@Override
	public void updateLastUnlock(User user, int unlock) throws HttpException, IOException {
		sqlUitl.updateUnlock(user, unlock);
		JSONObject json = JSONObject.fromObject(user);
		String str = json.toString();
		StringBuilder result = new StringBuilder(str.substring(0, str.length()-1));
		result.append(",");
		result.append("\"lastUnlock:\"");
		result.append(unlock);
		result.append("}");
		CasesConnection.uploadUserInfo(result.toString());
		
	}


}
