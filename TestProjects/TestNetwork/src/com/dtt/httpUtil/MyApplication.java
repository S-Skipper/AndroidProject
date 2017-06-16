package com.dtt.httpUtil;

import android.app.Application;
import android.content.Context;


//注意：要在AndroidManifest.xml中注册
/*<application
    android:name="com.dtt.httpUtil.MyApplication"
    ......
    */
public class MyApplication extends Application {

	private static Context context;
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
	}
	
	public static Context getContext() {
		return context;
	}
}
