package com.example.testphonegap;

import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//super.loadUrl(Config.getStartUrl());
        super.loadUrl("file:///android_asset/www/accelerometer.html");
	}
}
