package com.enggameforlearn;

import android.app.Activity;
import android.os.Bundle;

/***
 * 把图片截取为50x50
 * @author harmn
 *
 */
public class PortraitActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portrait);
	}
}
