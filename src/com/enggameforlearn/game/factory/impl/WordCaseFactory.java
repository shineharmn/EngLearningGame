package com.enggameforlearn.game.factory.impl;

import com.enggameforlearn.game.bo.Case;
import com.enggameforlearn.game.factory.IFactory;
/**
 * 
 * @author Administrator
 *
 */
public class WordCaseFactory implements IFactory{

	private static WordCaseFactory factory = new WordCaseFactory();
	
	protected Case caseInstance = null;
	
	private WordCaseFactory(){}
	
	public static WordCaseFactory getFactoryInstance(){
		return factory;
	}
	
	@Override
	public Case createCase() {
		if(caseInstance==null){
			caseInstance = new Case();
			
		}
		return caseInstance;
	}

}
