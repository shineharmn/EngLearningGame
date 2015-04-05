package com.enggameforlearn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.enggameforlearn.util.SingletonUser;
import com.enggameforlearn.web.ConnectFailDialog;
import com.enggameforlearn.web.PortraitConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/***
 * 把图片截取为50x50
 * @author harmn
 *
 */
@SuppressLint("NewApi")
public class PortraitActivity extends Activity{

	private static final int RETERN_VALUE = 2;
	
	private static final int CROP_PIC = 3;
	
	LinearLayout bg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portrait);
		Button select = (Button)findViewById(R.id.button_select);
		Button cancel = (Button)findViewById(R.id.button_cancel);
		bg = (LinearLayout)findViewById(R.id.portrait_bg);
		select.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*"); 
				startActivityForResult(intent, RETERN_VALUE);
				
			}
		});
		
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startActivity(new Intent(PortraitActivity.this,MainActivity.class));
			}
		});
		
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		switch (requestCode) {
		case RETERN_VALUE:
			if(data!=null){
				Uri uri = data.getData();
				Log.e("uri", uri.toString());
				cropImage(uri, 50, 50, CROP_PIC); 	
			}
			
			break;

		case CROP_PIC:
			
			Bitmap photo = null;  
		    Uri photoUri = data.getData();  
		    if (photoUri != null) {  
		         photo = BitmapFactory.decodeFile(photoUri.getPath());  
		      //   bg.setBackground(background)(R.id.portrait);
		         bg.setBackgroundResource(R.drawable.portrait);
		    }
		    if (photo == null) {  
		        Bundle extra = data.getExtras();  
		        if (extra != null) {  
		             photo = (Bitmap)extra.get("data");    

		             bg.setBackground(new BitmapDrawable(photo));

		             MainActivity.flag = true;
		             saveToSDCard("portrait",photo);
		             
		        }else{
		        	photo = BitmapFactory.decodeResource(this.getResources(),R.drawable.portrait);
		        }    
		    }
		    
		default:
			super.onActivityResult(requestCode, resultCode, data);
			startActivity(new Intent(PortraitActivity.this,MainActivity.class));
			break;
		}
     
		}
		
	
	private void cropImage(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");    
        intent.setDataAndType(uri, "image/*");    
        intent.putExtra("crop", "true");  
        //裁剪框的比例，1：1  
        intent.putExtra("aspectX", 1);    
        intent.putExtra("aspectY", 1);  
        //裁剪后输出图片的尺寸大小  
        intent.putExtra("outputX", outputX);     
        intent.putExtra("outputY", outputY);  
        //图片格式  
        intent.putExtra("outputFormat", "JPEG");  
        intent.putExtra("noFaceDetection", true);  
        intent.putExtra("return-data", true);    
        startActivityForResult(intent, requestCode);
	}
	
	private void saveToSDCard(String name,Bitmap bitmap){
		File f = new File(Environment.getExternalStorageDirectory().toString()+
				   File.separator +"englearning");
		 FileOutputStream fOut = null;
		 File photo;
		 try {  
			 if(!f.exists()){
				 f.mkdir();
			 }
			 photo = new File(f.getAbsoluteFile(),SingletonUser.getUser().getUsername()+"_portrait.JPEG");
			 if(!photo.exists()){
				 photo.createNewFile();
			 }
             fOut = new FileOutputStream(photo);  
             PortraitConnection.upload(photo);
     } catch (HttpException ex) {
 		ConnectFailDialog.showDialog(PortraitActivity.this);
 	} catch (IOException e) {  
            e.printStackTrace();  
     } 
		
			
		
		 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
		 
		 try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
}
