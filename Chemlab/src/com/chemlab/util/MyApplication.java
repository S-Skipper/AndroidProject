package com.chemlab.util;

import com.chemlab.objs.Contact;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApplication extends Application {

	
    public static String ID;
	public static String PW;
	//public static String RANK;
	
	private static Context context;
	private static SharedPreferences pref;
	
	public static Contact ME;
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
		pref = PreferenceManager.getDefaultSharedPreferences(context);
		
		ID = pref.getString("user_id","");
		PW = pref.getString("user_password","");
		
		/*private String id;
		private int imageId;*/
		ME = new Contact();
		setMEInfo();
		//RANK = pref.getString("rank", "0");
		/*
		{"error":"0","data":[
		   {"ID":"1111",
			"name":"1111",
			"rank_control":"管理权限",
			"rank_news":"管理权限",
			"rank_drug":"管理权限",
			"rank_equipment":"管理权限",
			"rank_experment":"管理权限",
			"rank_course":"管理权限",
			"rank_class":"管理权限",
			"rank_student":"管理权限",
			"rank_open":"管理权限",
			"token":"95w97buaHBdiGEC4nW",
			"token_Time":"2017-4-4 13:18:28"
		   }
		   ]}
		*/
	}
	
	public static void setMEInfo(){
		ME.setId(ID);
		ME.setName(getString("user_name"));
		ME.setIdebtity(getString("user_idebtity"));
		ME.setPhoneNumber(getString("user_telephone"));
		ME.setPhoneShort(getString("user_phone_short"));
		//ME.setWeChatNumber(getString("user_name"));
		ME.setQQNumber(getString("user_qq_number"));
		ME.setEmail(getString("user_email"));
		ME.setAddress(getString("user_address"));
		ME.setCreate_time(getString("user_create_time"));
		ME.setLast_time(getString("user_last_time"));
	}
	
	public static Context getContext() {
		return context;
	}
	
	public static void saveString(String key, String value) {
		pref.edit().putString(key, value).commit();
	}

	public static String getString(String key) {
		return pref.getString(key, "");
	}
	 
}
