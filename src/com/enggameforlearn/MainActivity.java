package com.enggameforlearn;

import android.app.Activity;
import android.content.Intent;
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
	
	private TextView account;
	
	UserService userService;
	
	public static final String INTENT_PORTRAIT = "com.enggameforlearn.PortraitActivity";
	
	public static final int PORTRAIT = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		userService = new UserServiceImpl(new SQLiteUtil(this));
		Intent intent = getIntent();
		Bundle data = intent.getExtras();
		user = (User)data.get("user");
		
		account =(TextView)findViewById(R.id.main_account);
		account.setText(user.getAccount());
		portrait = (ImageView)findViewById(R.id.portrait);
		Button logout = (Button)findViewById(R.id.main_logout);
	    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.portrait);
		Bitmap result = ScalePortrait.scaleRoundPortrait(bitmap);
		portrait.setImageBitmap(result);	
		portrait.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(MainActivity.INTENT_PORTRAIT), PORTRAIT);			
			}
		});
		
		logout.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				userService.logout(user);
				Intent intent = new Intent(MainActivity.this,LoginActivity.class);
				startActivity(intent);
								
				
			}
		});
		
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch (requestCode) {
		case PORTRAIT:
			
			break;

		default:
			break;
		}
	}
}
