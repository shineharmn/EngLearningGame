package com.enggameforlearn;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.user.service.impl.UserServiceImpl;
import com.enggameforlearn.util.SQLiteUtil;
import com.enggameforlearn.util.SingletonUser;
import com.enggameforlearn.web.ConnectFailDialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class GameResultActivity extends Activity{

	/**
	 * runningGame传来的关卡数
	 */
	private int playingGameNum;
	
	/**
	 * 当前用户
	 */
	private User user;
	
	private UserService userService;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent intent = getIntent();
		
		user = SingletonUser.getUser();
		playingGameNum = intent.getIntExtra("case", 1);
		userService = new UserServiceImpl(new SQLiteUtil(this));
		user.unLock(playingGameNum+1);
		try {
			userService.updateLastUnlock(user, user.getUnLockCases().get(user.getUnLockCases().size()-1));
		} catch (HttpException e) {
			ConnectFailDialog.showDialog(GameResultActivity.this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Button menu = (Button)findViewById(R.id.menu);
		Button next = (Button)findViewById(R.id.next);
		
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(GameResultActivity.this,ReadyGameActivity.class);
				startActivity(intent);
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(GameResultActivity.this,RunningGameActivity.class);
				intent.putExtra("case", playingGameNum+1);
				startActivity(intent);				
				
			}
		});
	}
	
	@Override    
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
	if(keyCode == KeyEvent.KEYCODE_BACK){      
	return  true;
	}  
	return  super.onKeyDown(keyCode, event);     

	}
	
}
