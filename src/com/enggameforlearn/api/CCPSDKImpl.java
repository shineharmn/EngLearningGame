package com.enggameforlearn.api;

import android.app.Activity;

import com.hisun.phone.core.voice.CCPCall;
import com.hisun.phone.core.voice.CCPCall.InitListener;

public class CCPSDKImpl implements ApiInterface{

	private static CCPSDKImpl ccpUtil = new CCPSDKImpl();
	
	private CCPSDKImpl(){}
	

	public static CCPSDKImpl getInstance(){
		return ccpUtil;
	}
	
	@Override
	public void Init(Activity act) {
		CCPCall.init(act, new InitListener() {
			
			@Override
			public void onInitialized() {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void onError(Exception arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


	@Override
	public int recognize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
