package com.chemlab.activity;

import com.chemlab.R;
import com.chemlab.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity {

	private WebView webView;
	private TextView titleTextView;
	private Button titleButton;
	
	//private ProgressDialog dialog = null;

	public static void actionStart(Context context, String title, String url) {
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("link", url);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		String titleString = getIntent().getStringExtra("title");
		String urlString = getIntent().getStringExtra("link");

		titleButton = (Button) findViewById(R.id.button_back);
		titleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(webView.canGoBack())
	            {
	                webView.goBack();//返回上一页面
	            }
	            else
	            {
	                finish();//退出程序
	            }
			}
		});

		titleTextView = (TextView) findViewById(R.id.textview_title);
		titleTextView.setText(titleString);

		webView = (WebView) findViewById(R.id.web_view);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webView.getSettings().setAppCacheEnabled(true);//是否使用缓存
		webView.getSettings().setDomStorageEnabled(true);//DOM Storage
		
		//logcat setting
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				LogUtil.d("WebViewActivity", 
						cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
				return true;
			}
		});
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
			/*
			@Override  
            public void onPageStarted(WebView view, String url, Bitmap favicon) {  
                if(dialog == null || !dialog.isShowing()){  
                	dialog = ProgressDialog.show(WebViewActivity.this,null,"页面加载中，请稍后..");
                }    
            }  
  
            @Override  
            public void onPageFinished(WebView view, String url) {  
            	dialog.dismiss();  
            } */
		});
		
		webView.loadUrl(urlString);

	}
}
