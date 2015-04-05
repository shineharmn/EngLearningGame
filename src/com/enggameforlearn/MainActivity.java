package com.enggameforlearn;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import com.enggameforlearn.util.SingletonUser;
import com.enggameforlearn.web.ConnectFailDialog;
import com.enggameforlearn.web.PortraitConnection;

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
	
	private UserService userService;
	
	public static final String INTENT_PORTRAIT = "com.enggameforlearn.PortraitActivity";
	
	public static final int PORTRAIT = 1;
	
	public static boolean flag = false;
	
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
			try {
				user = userService.login(account, password);
				if(user==null){
					this.showDialog("密码已经修改，请重新登录");
				}
			} catch (HttpException e1) {				
				ConnectFailDialog.showDialog(MainActivity.this);
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
			userName =(TextView)findViewById(R.id.main_account);
			userName.setText(user.getNickname());
			portrait = (ImageView)findViewById(R.id.portrait);
			Button logout = (Button)findViewById(R.id.main_logout);
			Button classicalModel = (Button)findViewById(R.id.button_startgame);
			TextView level = (TextView)findViewById(R.id.main_lv);
			level.setText("Lv"+user.getUnLockCases().size());
			File file = new File(Environment.getExternalStorageDirectory().toString()+
					   File.separator +"englearning");
			if(!file.exists()){		
					file.mkdir();	
			}
			Bitmap bitmap = null;			
			if(!flag){				
				try {
					bitmap = PortraitConnection.download(file, this);
				} catch (Exception e) {
					ConnectFailDialog.showDialog(MainActivity.this);
				}
			}else{
				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath()+"/"+SingletonUser.getUser().getUsername()+"_portrait.JPEG");
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

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("信息");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {	
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();	
				Intent intent = new Intent(MainActivity.this,MainActivity.class);		
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();	
	}
	

}
