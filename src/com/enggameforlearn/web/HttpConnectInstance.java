package com.enggameforlearn.web;

import org.apache.commons.httpclient.HttpClient;

public class HttpConnectInstance {

	private static HttpClient client = new HttpClient();
	
	public static HttpClient getInstance(){
		
		return client;
	}
}
