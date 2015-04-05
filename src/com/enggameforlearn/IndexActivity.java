package com.enggameforlearn;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 欢迎界面
 * @author Administrator
 *
 */
public class IndexActivity extends Activity  {

	@Override
	protected void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.activity_index);
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				startActivity(new Intent(IndexActivity.this,MainActivity.class));
				
			}
		};
		Timer timer = new Timer(true);
		timer.schedule(task, 3000);
		
		
	}
}
