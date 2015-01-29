package com.enggameforlearn;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.enggameforlearn.api.JsonParser;
import com.enggameforlearn.game.bo.Case;
import com.enggameforlearn.game.factory.IFactory;
import com.enggameforlearn.game.factory.impl.WordCaseFactory;
import com.enggameforlearn.game.service.GameService;
import com.enggameforlearn.game.service.impl.GameServiceImpl;
import com.enggameforlearn.util.SQLiteUtil;
import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.RecognizerListener;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechRecognizer;

public class RunningGameActivity extends Activity {

	private static String TAG = "WordFragment";
	/**
	 * 语音识别API
	 */
	private SpeechRecognizer mIat;

	
	/**
	 * 识别出的用户内容
	 */
	private String recognise;

	/**
	 * 关卡的答案
	 */
	private String answer;
	
	/**
	 * 关卡的问题
	 */
	 String question;

	/**
	 * 回答按钮
	 */
	private Button button;
	
	
	
	/**
	 * 当前关卡数
	 */
	int num;
	
	
	
	/**
	 * 当前关卡
	 */
	private Case playingCase;

	/**
	 * 用户是否开始答题
	 */
	private boolean clicked;
	
	private GameService gameService;

	protected void onCreate(Bundle s) {
		super.onCreate(s);
		setContentView(R.layout.activity_running);
		
	
		ApplicationInfo appInfo = getApplicationInfo();
		GridView view = (GridView) findViewById(R.id.gridview);
		button = (Button) findViewById(R.id.answer);
		ArrayList<HashMap<String, Object>> listItems = new ArrayList<>();
		IFactory factory = WordCaseFactory.getFactoryInstance();
		gameService = new GameServiceImpl(new SQLiteUtil(this),factory);
		Intent intent = getIntent();	
		num = intent.getIntExtra("case", 0);
		playingCase =  gameService.create(gameService.show().get(num));
		answer = playingCase.getAnswer();
		question = playingCase.getQuestion();
		for (String option:playingCase.getOptions()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage",getResources().getIdentifier(option, "drawable",appInfo.packageName));
			listItems.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,R.layout.item, new String[] { "ItemImage" },new int[] { R.id.ItemImage });
		view.setAdapter(simpleAdapter);
		
		button.setOnClickListener(new WordFragment(this));

		InitListener mInitListener = new InitListener() {

			@Override
			public void onInit(ISpeechModule module, int code) {
				Log.d(TAG, "SpeechRecognizer init() code = " + code);
				if (code == ErrorCode.SUCCESS) {
					button.setEnabled(true);
					
				}
			}
		};
		mIat = new SpeechRecognizer(this, mInitListener);
	}

	class WordFragment  implements OnClickListener {
		private SharedPreferences mSharedPreferences;
		public WordFragment(Activity activity) {
			String PREFER_NAME = "com.iflytek.setting";
			mSharedPreferences = activity.getSharedPreferences(PREFER_NAME, Activity.MODE_PRIVATE);
			clicked = false;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.answer:
				if (clicked) {
					System.out.println("clicked true:" + clicked);
					mIat.stopListening(mRecognizerListener);
					Log.d("stop_bottom", "stopListening");
				} else {
					System.out.println("clicked false:"+clicked);				
					setParam();
					mIat.startListening(mRecognizerListener);
					clicked=true;
					button.setText("answering");
				}

				break;

			default:
				break;
			}

		}
		
		public void setParam(){
			
			mIat.setParameter(SpeechConstant.LANGUAGE, mSharedPreferences.getString("iat_language_preference", "en_us"));
			mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
			mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "500"));
			
			String param = null;
			param = "asr_ptt="+mSharedPreferences.getString("iat_punc_preference", "0");
			mIat.setParameter(SpeechConstant.PARAMS, param+",asr_audio_path=/sdcard/iflytek/wavaudio.pcm");

		}

	}

	private RecognizerListener mRecognizerListener = new RecognizerListener.Stub() {

		@Override
		public void onVolumeChanged(int v) throws RemoteException {
		}

		@Override
		public void onResult(final RecognizerResult result, boolean isLast)
				throws RemoteException {
			if (null != result) {
				// 显示
				Log.d(TAG, "recognizer result：" + result.getResultString());
				recognise = JsonParser.parseIatResult(result.getResultString());
				System.out.println("识别结果" + recognise + "---");
				
				if (recognise.equals(answer)) {
					Intent intent = new Intent(RunningGameActivity.this,GameResultActivity.class);				
					
					intent.putExtra("case", num);
					startActivity(intent);
					
				} else {
					Intent intent = new Intent(RunningGameActivity.this,ReadyGameActivity.class);			
					
					intent.putExtra("case", num);
					startActivity(intent);	
					
				}
				clicked = false;
			} else {
				clicked = false;
				Log.d(TAG, "recognizer result : null");
				
			}

		}

		@Override
		public void onError(int errorCode) throws RemoteException {			
		}

		@Override
		public void onEndOfSpeech() throws RemoteException {			
		}

		@Override
		public void onBeginOfSpeech() throws RemoteException {		
		}
	};

}
