package com.enggameforlearn.web;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ConnectFailDialog {

	public static void showDialog(Activity act){
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setTitle("信息");
		builder.setMessage("网络异常，请检查网络！");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();							
			}
		});
		AlertDialog alert = builder.create();
		alert.show();	
	}
}
