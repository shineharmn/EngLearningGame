package com.enggameforlearn.user.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * @author Administrator
 *
 */
public class User implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 账号
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户名
	 */
	private String nickname;
	
	/**
	 * 保存的是已经解锁了的关卡的id
	 */
	private List<Integer> unLockCases = new ArrayList<Integer>();
	
	public User(String name){
	
		username = name;
		
		
	}
	
	/**
	 * 当前一关攻略后，若下一关不在list中，就调用该方法解锁下一关
	 * @param caseId
	 */
	public void unLock(int caseId){
		if(unLockCases.indexOf(caseId)==-1){
			unLockCases.add(caseId);
		}		
	}
	
	/**
	 * 判断是否解锁了该关卡
	 * @param caseId
	 * @return
	 */
	public boolean isLock(int caseId){
		return unLockCases.contains(caseId);
	}

	public boolean checkPassword(String pwd){
		return password.equals(pwd);
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String acc){
		this.username = acc;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setLockCases(List<Integer> lockCases) {
		this.unLockCases = lockCases;
	}

	public List<Integer> getUnLockCases() {
		return unLockCases;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	public void setNickname(String username){
		this.nickname = username;
	}
	
	
}
