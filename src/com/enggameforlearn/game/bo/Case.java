package com.enggameforlearn.game.bo;

import java.util.ArrayList;

/**
 * 游戏案例
 * @author Administrator
 *
 */
public class Case {
	
	protected int caseId;

	/**
	 * 选项
	 */
	private ArrayList<AbstractOption> options;
	
	/**
	 * 答案
	 */
	private AbstractOption answer;
	
	
	
	public Case(){
		
	}
	
	
	
	public int getCaseId() {
		return caseId;
	}



	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}



	public void setOptions(ArrayList<AbstractOption> mOptions){
		this.options = mOptions;
	}
	
	public void setAnswer(AbstractOption answer){
		this.answer = answer;
	}
	public ArrayList<AbstractOption> getOptions(){
		return options;
	}
	public AbstractOption getAnswer(){
		return answer;
	}
	
	public boolean check(AbstractOption option){
		if(answer.getId()==option.getId()){
			return true;
		}else{
			return false;
		}
	}
}
