package com.enggameforlearn.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class ScalePortrait {

	public static Bitmap scaleRoundPortrait(Bitmap bitmap){
		if(bitmap!=null){	
		Bitmap result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		Paint paint = new Paint();
		Rect rect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
	//	canvas.drawARGB(0, 0, 0, 0);	
		canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null,rect, paint);	
		return result;
		}else {
			return Bitmap.createBitmap(0, 0, Config.ARGB_8888);
		}
		
		
	}
}
