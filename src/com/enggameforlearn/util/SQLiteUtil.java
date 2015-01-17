package com.enggameforlearn.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enggameforlearn.game.bo.AbstractOption;
import com.enggameforlearn.game.bo.WordOption;
import com.enggameforlearn.user.bo.User;

public class SQLiteUtil extends SQLiteOpenHelper {

	
	private static final String DB_NAME = "english.db"; // 数据库名称
	private static final int version = 1; // 数据库版本
	private static final String CASE_TABLE = "CREATE TABLE IF NOT EXISTS Cases (caseId integer primary key autoincrement," +
			"option1 integer not null,option2 integer not null,option3 integer not null," +
			"option4 integer not null,option5 integer not null,option6 integer not null," +
			"option7 integer not null,option8 integer not null,option9 integer not null,answer integer not null)";	
	
	private static final String OPTION_TABLE = "CREATE TABLE IF NOT EXISTS Option (optionId integer primary key autoincrement," +
			"optionName varchar(20) not null,link varchar(60) not null)";
	
	private static final String USER_TABLE = "CREATE TABLE IF NOT EXISTS User (account varchar(20) primary key,userName varchar(12),password varchar(10) not null)";
	
	private static final String LAST_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS LastUnlock (account varchar(20) primary key,lastCaseId integer)";

	public SQLiteUtil(Context context) {
		super(context, DB_NAME, null, version);
		
	}

	/**
	 * 获取某个id的答案
	 * @param id
	 * @return
	 */
	public synchronized AbstractOption getAnswer(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases where caseId=?",
				new String[] { String.valueOf(id) });
		AbstractOption answer = new WordOption(-1, "", "");
		if (c.moveToFirst()) {
			answer = findOption(c, "answer");
		}
		c.close();
		db.close();
		return answer;
	}
	
	/**
	 * 获取某个id的选项
	 * @param id
	 * @return
	 */
	public synchronized ArrayList<AbstractOption> getOptions(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases where caseId=?",
				new String[] { String.valueOf(id) });
		ArrayList<AbstractOption> options = new ArrayList<AbstractOption>();
		if(c.moveToFirst()){
			for(int i = 1;i<=9;i++){
				options.add(findOption(c, "option"+i));
			}
		}
		c.close();
		db.close();
		return options;
	}
	
	/**
	 * 获取所有的cases
	 * @return
	 */
	public synchronized ArrayList<Integer> getAllcases(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases ",
				new String[] {});
		ArrayList<Integer> cases = new ArrayList<Integer>();
		while(c.moveToNext()){
			cases.add(c.getInt(c.getColumnIndex("caseId")));
		}
		c.close();
		db.close();
		return cases;
	}	
	
	/**
	 * 获得某个用户
	 * @param userName
	 * @return
	 */
	public synchronized User getUser(String userName){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from User where account=?", new String[]{userName});
		User user = null;
		if(c.moveToFirst()){	
		
			user = new User(userName);	
			user.setUsername(c.getString(c.getColumnIndex("userName")));
			user.setPassword((c.getString(c.getColumnIndex("password"))));
		
		}
		c.close();
		db.close();
		return user;
	}
	
	/**
	 * 获得用户的最后一个可以玩的关卡
	 * @param userName
	 * @return
	 */
	public synchronized Integer getLastUnlock(String userName){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from LastUnlock where account=?", new String[]{userName});
		int id = 0;
		if(c.moveToFirst()){	
			id = c.getInt(c.getColumnIndex("lastCaseId"));		
		}
		c.close();
		db.close();
		return id;
	} 
	
	public synchronized void updateUnlock(User user,int unlock){
		
	}
	
	public synchronized void addUser(User obj){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "insert into LastUnlock(account,lastCaseId) values ('"+obj.getAccount()+"',"+0+") ";
		String add = "insert into User(account,userName,password) values ('"+obj.getAccount()+"','"+obj.getUsername()+"','"+obj.getPassword()+"') ";
		db.execSQL(add);
		db.execSQL(sql);
		db.close();
	}
	
	
	
	public AbstractOption findOption(Cursor c,String col){
		SQLiteDatabase db = this.getReadableDatabase();
		int id =  c.getInt(c.getColumnIndex(col));
		AbstractOption answer = new WordOption(-1, "", "");
		Cursor cursor = db.rawQuery("select * from Option where optionId=?",
				new String[] { String.valueOf(id) });
		if(cursor.moveToFirst()){
			String optionName = cursor.getString(cursor
					.getColumnIndex("optionName"));
			String link = cursor.getString(cursor.getColumnIndex("link"));
			answer = new WordOption(id, optionName, link);			
		}
		cursor.close();
		db.close();
		return answer;
	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CASE_TABLE);
		db.execSQL(OPTION_TABLE);
		db.execSQL(USER_TABLE);
		db.execSQL(LAST_RECORD_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	

	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private String getAdd(Object obj){
//		StringBuilder sb = new StringBuilder("insert into ");
//		Class c = obj.getClass(); //获得类
//		Method[] methods = c.getMethods();  //获得方法
//		//Field[] fields = c.getFields();  //获得属性
//		String name = c.getName();
//		
//		String tableName = name.substring(name.lastIndexOf(".")+1);
//		sb.append(tableName+"(");
//		List<String> mList = new ArrayList<String>();
//		List values = new ArrayList();
//		for(Method method:methods){
//			String methodName = method.getName();
//			if(methodName.startsWith("get")&&!methodName.startsWith("getClass")){
//				mList.add(methodName.substring(3));
//				try {
//					Object value = method.invoke(obj);
//					if(value instanceof String){
//						values.add("\""+value+"\"");
//					}else{
//						values.add(value);
//					}
//				} catch (Exception e) {
//					 
//					e.printStackTrace();
//				} 
//			}
//		}
//		for(int i = 0;i<mList.size();i++){
//			if(i<mList.size()-1){
//				sb.append(mList.get(i));
//				sb.append(",");
//			}else{
//				sb.append(mList.get(i));
//				sb.append(")");
//				sb.append("values(");
//			}
//		}
//	      for (int i = 0; i < values.size(); i++) {
//	            if (i < values.size() - 1) {
//	               sb.append(values.get(i));
//	               sb.append(",");
//	            } else {
//	                sb.append(values.get(i));
//	                sb.append(")");
//	            }
//	      }
//	      
//	      return sb.toString();
//	}

}
