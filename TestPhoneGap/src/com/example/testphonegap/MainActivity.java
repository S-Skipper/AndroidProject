package com.example.testphonegap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
import org.apache.cordova.DroidGap;

import android.os.Bundle;

public class MainActivity extends DroidGap {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//super.loadUrl(Config.getStartUrl());
        super.loadUrl("file:///android_asset/www/accelerometer.html");
	}
}*/

public class MainActivity extends Activity{
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webView = new WebView(MainActivity.this);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
		webView.getSettings().setDomStorageEnabled(true);//DOM Storage
		//webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); 
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		setContentView(webView);
		
		webView.loadUrl("http://bxw2359770225.my3w.com/");
		//webView.loadUrl("http://www.baidu.com/");
	}
}