package com.enggameforlearn.game.service;

import java.util.ArrayList;

import com.enggameforlearn.game.bo.Case;

public interface GameService {

	/**
	 * 创建一个游戏案例，并设置其外蕴状态。鸡开始一个游戏的情景
	 * @param id
	 * @return
	 */
	Case create(int id);
	
	/**
	 * 进入主界面后，会显示出所有的关卡，show返回的是各个case的id
	 * @return
	 */
	ArrayList<Integer> show();
}
