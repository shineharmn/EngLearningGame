package com.enggameforlearn.web;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class CasesConnection {

	public static void uploadUserInfo(String json) throws HttpException, IOException{
		GetMethod get = new GetMethod("http://127.0.0.1:8080/engspeaker/user.do?method=update&updateInfo="+json);
		HttpConnectInstance.getInstance().executeMethod(get);
		
		return;
	}
}
