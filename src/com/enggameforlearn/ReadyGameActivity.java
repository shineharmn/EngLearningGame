package com.enggameforlearn;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.enggameforlearn.game.factory.IFactory;
import com.enggameforlearn.game.factory.impl.WordCaseFactory;
import com.enggameforlearn.game.service.GameService;
import com.enggameforlearn.game.service.impl.GameServiceImpl;
import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.util.SQLiteUtil;
import com.enggameforlearn.util.SingletonUser;

public class ReadyGameActivity extends Activity{

	/**
	 * 用户
	 */
	User user;
	
	private GameService gameService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readygame);
		ApplicationInfo appInfo = getApplicationInfo();	
		user = SingletonUser.getUser();		
		IFactory factory = WordCaseFactory.getFactoryInstance();
		gameService = new GameServiceImpl(new SQLiteUtil(this),factory);
		
		for(int i = 1;i<=gameService.show().size();i++){	
			Button button = (Button)findViewById(getResources().getIdentifier("mission_"+i, "id", appInfo.packageName));
			if(user.isLock(i)){
				button.setOnClickListener(new StartListener(i));
				button.setTextColor(getResources().getColor(R.drawable.lightgreen));
			}else{
				button.setTextColor(Color.GRAY);
			}
		}
		
		
	}
	
	class StartListener implements OnClickListener{
		private int num;
		public StartListener(int i){
			num = i;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ReadyGameActivity.this,RunningGameActivity.class);
			intent.putExtra("case", num);
			
			startActivity(intent);
			
			
		}
		
	}
}
