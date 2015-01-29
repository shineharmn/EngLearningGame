package com.enggameforlearn.util;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enggameforlearn.user.bo.User;

public class SQLiteUtil extends SQLiteOpenHelper {

	
	private static final String DB_NAME = "english.db"; // 数据库名称
	private static final int version = 1; // 数据库版本
	private static final String CASE_TABLE = "CREATE TABLE IF NOT EXISTS Cases (caseId integer primary key autoincrement," +
			"option1 varchar(10) ,option2 varchar(10) ,option3 varchar(10)," +
			"option4 varchar(10) ,option5 varchar(10),option6 varchar(10) ," +
			"option7 varchar(10) ,option8 varchar(10) ,option9 varchar(10) ,question varchar(50) not null,answer varchar(10) not null)";	
	
//	private static final String OPTION_TABLE = "CREATE TABLE IF NOT EXISTS Option (optionId integer primary key autoincrement," +
//			"optionName varchar(10) not null,link varchar(10) not null)";
	
	private static final String USER_TABLE = "CREATE TABLE IF NOT EXISTS User (account varchar(20) primary key,userName varchar(12),password varchar(10) not null)";
	
	private static final String LAST_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS LastUnlock (account varchar(20) primary key,lastCaseId integer)";


	
	public SQLiteUtil(Context context) {
		super(context, DB_NAME, null, version);
		
		
		
	}

	/**
	 * 获取某个题目id的问题
	 * @param id
	 * @return
	 */
	public synchronized String getAnswer(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases where caseId=?",
				new String[] { String.valueOf(id) });
		String answer = "";
		if (c.moveToFirst()) {
			answer = c.getString(c.getColumnIndex("answer"));
		}
		c.close();
		db.close();
		return answer;
	}
	
	/**
	 * 获取某个题目id的问题
	 * @param id
	 * @return
	 */
	public synchronized String getQuestion(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases where caseId=?",
				new String[] { String.valueOf(id) });
		String question = "";
		if (c.moveToFirst()) {
			question = c.getString(c.getColumnIndex("question"));
		}
		c.close();
		db.close();
		return question;
	}
	
	/**
	 * 获取某个题目id的问题
	 * @param id
	 * @return
	 */
	public synchronized ArrayList<String> getOptions(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("select * from Cases where caseId=?",
				new String[] { String.valueOf(id) });
		ArrayList<String> options = new ArrayList<String>();
		if(c.moveToFirst()){
			for(int i = 1;i<=9;i++){
				options.add(c.getString(c.getColumnIndex("option"+i)));
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
	public  synchronized User getUser(String userName){
		SQLiteDatabase db = getReadableDatabase();
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
	
	
	
	
	
//	public AbstractOption findOption(Cursor c,String col){
//		SQLiteDatabase db = this.getReadableDatabase();
//		int id =  c.getInt(c.getColumnIndex(col));
//		AbstractOption answer = new WordOption(-1, "", "");
//		Cursor cursor = db.rawQuery("select * from Option where optionId=?",
//				new String[] { String.valueOf(id) });
//		if(cursor.moveToFirst()){
//			String optionName = cursor.getString(cursor
//					.getColumnIndex("optionName"));
//			String link = cursor.getString(cursor.getColumnIndex("link"));
//			answer = new WordOption(id, optionName, link);			
//		}
//		cursor.close();
//		db.close();
//		return answer;
//	}

	

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(CASE_TABLE);
	//	db.execSQL(OPTION_TABLE);
		db.execSQL(USER_TABLE);
		db.execSQL(LAST_RECORD_TABLE);
		
//		Properties options = OptionsUtil.getProperty();
//		Iterator<Entry<Object, Object>> it = options.entrySet().iterator();
//		while(it.hasNext()){
//			Map.Entry<Object, Object> entry=(Map.Entry<Object, Object>)it.next();
//			String optionName = ((String)entry.getKey()).substring(2);
//			String link = (String)entry.getValue();						
//			String sql = "insert into Option(optionName,link) values ('"+optionName+"','"+link+"')";
//			db.execSQL(sql);
//		}
		String sql = "insert into Cases(option1,option2,option3,option4,option5,option6,option7,option8,option9,question,answer) values (" +
				"'img_11','img_12','img_13','img_14','img_15','img_16','img_17','img_18','img_19','请找出以下不同的一项','teddy')";
		String sql2 = "insert into Cases(option1,option2,option3,option4,option5,option6,option7,option8,option9,question,answer) values (" +
				"'img_12','img_11','img_14','img_13','img_15','img_16','img_17','img_18','img_19','请找出以下重复的一项','pineapple')";
		String sql3 = "insert into Cases(option1,option2,option3,option4,option5,option6,option7,option8,option9,question,answer) values (" +
				"'img_12','img_14','img_11','img_13','img_16','img_15','img_18','img_17','img_19','请找出以下不同的两项','teddy pineapple')";
		db.execSQL(sql);
		db.execSQL(sql2);
		db.execSQL(sql3);
		
		
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
