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
	private ArrayList<String> options;
	
	/**
	 * 问题
	 */
	private String question;
	
	/**
	 * 答案
	 */
	private String answer;
	
	
	
	public Case(){
		
	}
	
	
	
	public int getCaseId() {
		return caseId;
	}



	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}



	public void setOptions(ArrayList<String> mOptions){
		this.options = mOptions;
	}
	
	public void setAnswer(String answer){
		this.answer = answer;
	}
	public ArrayList<String> getOptions(){
		return options;
	}
	public String getAnswer(){
		return answer;
	}
	
	
	
	public String getQuestion() {
		return question;
	}



	public void setQuestion(String question) {
		this.question = question;
	}



	public boolean check(String option){
		return answer.equals(option);
	}
}
