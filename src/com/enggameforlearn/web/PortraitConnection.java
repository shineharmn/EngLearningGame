package com.enggameforlearn.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.enggameforlearn.R;
import com.enggameforlearn.util.SingletonUser;

public class PortraitConnection {

	/**
	 * 下载头像
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static Bitmap download(File file,Activity activity) throws HttpException, IOException{
		
		File image = new File(file.getAbsolutePath()+"/"+SingletonUser.getUser().getUsername()+"_portrait.JPEG");
		if(!image.exists()){
			image.createNewFile();
		}		
		PostMethod post = new PostMethod("http://127.0.0.1:8080/engspeaker/user.do?method=download&image="+image.getName());
		HttpConnectInstance.getInstance().executeMethod(post);
		if(post.getResponseBody().length!=0){
			ByteArrayInputStream bais = new ByteArrayInputStream(post.getResponseBody()); 		
			BufferedImage bi1 =ImageIO.read(bais);	
			ImageIO.write(bi1, "jpg", image);
			return BitmapFactory.decodeFile(image.getAbsolutePath());
		}
		else{
			return BitmapFactory.decodeResource(activity.getResources(),R.drawable.portrait);
		}
		
		
	}
	
	/**
	 * 上传头像
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void upload(File file) throws HttpException, IOException { 
		
		String url = "http://127.0.0.1:8080/engspeaker/user.do?method=portrait";
		PostMethod post = new PostMethod(url);
		Part[] parts = {new FilePart(file.getName(), file)};
		post.setRequestEntity(new MultipartRequestEntity(parts,post.getParams()));
		
		HttpConnectInstance.getInstance().getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		 HttpConnectInstance.getInstance().executeMethod(post);
	}
	
}
