package com.enggameforlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.user.service.impl.UserServiceImpl;
import com.enggameforlearn.util.SQLiteUtil;

public class RegisterActivity extends Activity{

	
	UserService userService;
	
	SharedPreferences sp;
	
	@Override
	protected void  onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);	
		userService = new UserServiceImpl(new SQLiteUtil(this));
		sp = getSharedPreferences("userInfo", 0);
		findViewById(R.id.register_button).setOnClickListener(new RegisterListener());
		findViewById(R.id.register_account_edit).setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String account = ((EditText)findViewById(R.id.register_account_edit)).getText().toString();
				String msg = "";
				if(!hasFocus){
				if(account.equals("")){
					msg = "账号不能为空";
				}else if(userService.isRegister(account)){
					msg = "该账号已被注册";
				}else{
					msg = "该账号可以使用";
				}
					showDialog(msg);
					
				}
			}
		});
	}
	
	class RegisterListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			String account = ((EditText)findViewById(R.id.register_account_edit)).getText().toString();
			String password = ((EditText)findViewById(R.id.register_password_edit)).getText().toString();
			String conform = ((EditText)findViewById(R.id.register_conform_edit)).getText().toString();
			String userName = ((EditText)findViewById(R.id.register_name_edit)).getText().toString();
			if(conform.equals(password)){
				User user = null;
				try {
					user = userService.register(account, userName, password);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(user==null){
					showDialog("该账号已被注册");
				}else{					
					SharedPreferences.Editor editor =sp.edit();
					editor.putString("USER_NAME", account);
					editor.putString("PASSWORD", password);
					editor.commit();
					showDialog("注册成功",user);
					
					
				}
			}else{
				showDialog("两次输入的密码不一致");
			}
			
			
		}
		
	}
	private void showDialog(String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		builder.setTitle("信息");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();							
			}
		});
		AlertDialog alert = builder.create();
		alert.show();	
	}
	private void showDialog(String msg,final User user){
		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
		builder.setTitle("信息");
		builder.setMessage(msg);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {	
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();	
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);		
				startActivity(intent);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();	
	}
	
}
