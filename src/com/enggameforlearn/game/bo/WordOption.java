package com.enggameforlearn.game.bo;

/**
 * 选项
 * @author Administrator
 *
 */
public class WordOption extends AbstractOption{

	/**
	 * 图片链接
	 */
	private String link;
	
	public WordOption(int id ,String option,String mlink){
		super(id,option);
		this.link = mlink;
	}

	@Override
	public String getLink() {
		return link;
	}
	
	
}
