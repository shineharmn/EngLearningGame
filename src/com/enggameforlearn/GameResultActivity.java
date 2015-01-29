package com.enggameforlearn;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.util.SingletonUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Intent intent = getIntent();
		
		user = SingletonUser.getUser();
		playingGameNum = intent.getIntExtra("case", 0);
		user.unLock(playingGameNum+1);
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
	
}
