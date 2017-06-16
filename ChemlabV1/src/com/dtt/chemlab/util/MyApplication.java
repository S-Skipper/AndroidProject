package com.dtt.chemlab.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApplication extends Application {

	private static Context context;
    public static String ID;
	public static String PW;
	public static String RANK;
	public static String HOST;
	
	private SharedPreferences pref;
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		ID = pref.getString("name", "1111");
		PW = pref.getString("password", "1111");
		RANK = pref.getString("rank", "0");
		HOST = "211.66.136.32";
	}
	
	public static Context getContext() {
		return context;
	}
	
	 
}
