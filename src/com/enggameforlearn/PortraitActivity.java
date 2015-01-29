package com.enggameforlearn;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/***
 * 把图片截取为50x50
 * @author harmn
 *
 */
public class PortraitActivity extends Activity{

	private static final int RETERN_VALUE = 2;
	
	private static final int CROP_PIC = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portrait);
		Button select = (Button)findViewById(R.id.button_select);
		Button cancel = (Button)findViewById(R.id.button_cancel);
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
		    }
		    if (photo == null) {  
		        Bundle extra = data.getExtras();  
		        if (extra != null) {  
		             photo = (Bitmap)extra.get("data");    
		             ByteArrayOutputStream stream = new ByteArrayOutputStream();    
		             photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);  
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
	
}
