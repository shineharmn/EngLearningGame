package com.enggameforlearn.web;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class LoginConnection {

	

	public static String login(String username, String password) throws HttpException, IOException{
		String jsonstr = "";
		GetMethod get = new GetMethod("http://127.0.0.1:8080/engspeaker/user.do?method=login&username="+username+"&password="+password);
		
			HttpConnectInstance.getInstance().executeMethod(get);
			jsonstr = new String(get.getResponseBodyAsString().getBytes(),"UTF-8");
						
		
			return jsonstr;
		 
	}
}
