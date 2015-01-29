package com.enggameforlearn;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.user.service.impl.UserServiceImpl;
import com.enggameforlearn.util.SQLiteUtil;
import com.enggameforlearn.util.ScalePortrait;

public class MainActivity extends Activity {

	/**
	 * 登录的用户
	 */
	private User user;
	
	/**
	 * 头像
	 */
	private ImageView portrait;
	
	
	private TextView userName;
	
	UserService userService;
	
	public static final String INTENT_PORTRAIT = "com.enggameforlearn.PortraitActivity";
	
	public static final int PORTRAIT = 1;
	
	SharedPreferences sp;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		userService = new UserServiceImpl(new SQLiteUtil(this));
		sp = getSharedPreferences("userInfo",0);	
		String account = sp.getString("USER_NAME", "");
		String password = sp.getString("PASSWORD", "");
		if(account==""){
			Intent intent = new Intent(MainActivity.this,LoginActivity.class);
			startActivity(intent);
			
		}else{
		//user = SingletonUser.getUser();
		user = userService.login(account, password);
		
		userName =(TextView)findViewById(R.id.main_account);
		userName.setText(user.getUsername());
		portrait = (ImageView)findViewById(R.id.portrait);
		Button logout = (Button)findViewById(R.id.main_logout);
		Button classicalModel = (Button)findViewById(R.id.button_startgame);
		TextView level = (TextView)findViewById(R.id.main_lv);
		level.setText("Lv"+user.getUnLockCases().size());
		Bitmap bitmap = BitmapFactory.decodeFile("image/outputFormat.JPEG");
		if(bitmap==null){
			 bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.portrait);
		}    
		Bitmap result = ScalePortrait.scaleRoundPortrait(bitmap);
		portrait.setImageBitmap(result);	
		portrait.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,PortraitActivity.class));			
			}
		});
		
		logout.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userService.logout(user);
				SharedPreferences.Editor editor =sp.edit();
				editor.putString("USER_NAME", "");
				editor.putString("PASSWORD", "");
				editor.commit();
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
								
				
			}
		});
		
		classicalModel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,ReadyGameActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
		
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

}
