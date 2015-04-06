package com.enggameforlearn.web;

import java.io.File;
import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

public class CasesConnection {

	public static void uploadUserInfo(String json) throws HttpException, IOException{
		GetMethod get = new GetMethod("http://127.0.0.1:8080/engspeaker/user.do?method=update&updateInfo="+json);
		HttpConnectInstance.getInstance().executeMethod(get);
		
		return;
	}
	
	public void downloadGame(){
		
		
	}
	
	/****
	  * 解压
	  * 
	  * @param zipFile
	  *            zip文件
	  * @param destinationPath
	  *            解压的目的地点
	  *
	  */
	 public static void unZip(File zipFile, String destinationPath) {
	  
	  Project proj = new Project();
	  Expand expand = new Expand();
	  expand.setProject(proj);
	  expand.setTaskType("unzip");
	  expand.setTaskName("unzip");
	  expand.setSrc(zipFile);
	  expand.setDest(new File(destinationPath));
	 // expand.setEncoding(ENCODE);
	  expand.execute();
	  System.out.println("unzip done!!!");
	 }
}
