package com.enggameforlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.enggameforlearn.user.bo.User;
import com.enggameforlearn.user.service.UserService;
import com.enggameforlearn.user.service.impl.UserServiceImpl;
import com.enggameforlearn.util.SQLiteUtil;

public class LoginActivity extends Activity{

	EditText account_input;
	EditText password_input;
	UserService userService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
		userService = new UserServiceImpl(new SQLiteUtil(this));
		findViewById(R.id.register_text).setOnClickListener((View.OnClickListener) new RegisterListener());
		findViewById(R.id.signin_button).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				account_input = (EditText)findViewById(R.id.account_edit);
				password_input = (EditText)findViewById(R.id.password_edit);
				String userName = account_input.getText().toString();
				String password = password_input.getText().toString();
				if(userName!=""&&password!=""){								
				User user = userService.login(userName, password);
				if(user!=null){
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", user);
					intent.putExtras(bundle);
					startActivity(intent);
				}else{
					showDialog("用户名或密码不正确");
				}
				}else{
					showDialog("信息不能为空");
				}
			}
		});
		
	
	}
	private void showDialog(String str){
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		builder.setTitle("信息");
		builder.setMessage(str);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();							
			}
		});
		AlertDialog alert = builder.create();
		alert.show();		
	}
	
	class RegisterListener implements View.OnClickListener{

		public RegisterListener(){}
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
			
		}
		
	}
}
