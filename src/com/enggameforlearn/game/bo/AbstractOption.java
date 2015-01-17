package com.enggameforlearn.game.bo;


public abstract class AbstractOption {

	private int id;
	
	private String optionName;

	public AbstractOption(int id,String optionName){
		setId(id);
		setOptionName(optionName);
	}
	
	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getOptionName() {
		return optionName;
	}

	private void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	
	public abstract String getLink();
	
}
