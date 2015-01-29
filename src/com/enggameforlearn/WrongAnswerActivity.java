package com.enggameforlearn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WrongAnswerActivity extends Activity{
	
	/**
	 * runningGame传来的关卡数
	 */
	private int playingGameNum;
	
	

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wrongresult);
		Intent intent = getIntent();		
		playingGameNum = intent.getIntExtra("case", 0);
		Button menu = (Button)findViewById(R.id.wrong_menu);
		Button restart = (Button)findViewById(R.id.restart);
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(WrongAnswerActivity.this,ReadyGameActivity.class);
				startActivity(intent);
			}
		});
		
		restart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WrongAnswerActivity.this,RunningGameActivity.class);
				intent.putExtra("playingGame", playingGameNum);
				startActivity(intent);				
				
			}
		});
	}
}
