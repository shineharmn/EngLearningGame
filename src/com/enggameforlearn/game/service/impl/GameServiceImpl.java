package com.enggameforlearn.game.service.impl;

import java.util.ArrayList;

import com.enggameforlearn.game.bo.Case;
import com.enggameforlearn.game.factory.IFactory;
import com.enggameforlearn.game.service.GameService;
import com.enggameforlearn.util.SQLiteUtil;
/**
 * 
 * @author Administrator
 *
 */
public class GameServiceImpl implements GameService{

	/**
	 * case创建工厂类
	 */
	protected IFactory factory;
	/**
	 * 数据库操作类
	 */
	protected SQLiteUtil sqliteUtil;
	 
	
	public GameServiceImpl(SQLiteUtil sqliteUtil,IFactory factory){
		this.sqliteUtil = sqliteUtil;
		this.factory = factory;
	}
	
	
	@Override
	public Case create(int id) {
		if(factory!=null){
			Case caseInstance = factory.createCase();
			caseInstance.setCaseId(id);
			caseInstance.setAnswer(sqliteUtil.getAnswer(id));	
			caseInstance.setOptions(sqliteUtil.getOptions(id));
			caseInstance.setQuestion(sqliteUtil.getQuestion(id));
			return caseInstance;
		}else{
			return null;
		}
		
	}

	@Override
	public ArrayList<Integer> show() {
		return sqliteUtil.getAllcases();
		
	}

	
}
