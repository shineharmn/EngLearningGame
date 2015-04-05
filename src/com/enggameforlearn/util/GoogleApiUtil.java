package com.enggameforlearn.util;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.enggameforlearn.api.ApiInterface;

/**
 * 谷歌api
 * @author harmn
 *
 */
public class GoogleApiUtil implements ApiInterface{

	public static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
	
	Activity activity;
	
	PackageInfo packageInfo;
	
	private static GoogleApiUtil instance = new GoogleApiUtil();
	
	private GoogleApiUtil(){}
	
	public static GoogleApiUtil getInstance(){
		return instance;
	}
	
	@Override
	public void Init(Activity act) {
		activity = act;
//		String packageName = activity.getString(R.string.voice);
//		 try {
//			packageInfo = activity.getPackageManager().getPackageInfo(packageName, 0);
//		} catch (NameNotFoundException e) {
//			packageInfo = null;
//			e.printStackTrace();
//		}
//		if(packageInfo == null){
//			Uri uri = Uri.parse(packageName);
//			Intent it = new Intent(Intent.ACTION_VIEW, uri);
//			activity.startActivity(it);
//		}
		
	}

	@Override
	public int recognize() {
		int requestCode = VOICE_RECOGNITION_REQUEST_CODE;
		 try {  
		        // 通过Intent传递语音识别的模式，开启语音  
		        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);  
		        // 语言模式和自由模式的语音识别  
		        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,  
		                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  
		        // 提示语音开始  
		        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音");  
		        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "eng");   
		        // 开始语音识别  
		        activity.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		        Toast.makeText(activity.getApplicationContext(), "找不到语音设备", Toast.LENGTH_SHORT).show();
		        
		    }  
		 
		 return requestCode;
	}

}
