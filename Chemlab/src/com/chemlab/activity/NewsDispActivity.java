package com.chemlab.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.objs.News;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;

public class NewsDispActivity extends Activity {
	
	private TextView titleText;
	private Button backButton;
	private News news;
	private WebView newsWebView;
	private String html="";
	
	public static void actionStart(Context context,String title, News newsExtra) {
		Intent intent = new Intent(context, NewsDispActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("news", newsExtra);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_news_display);
		
		news = (News) getIntent().getSerializableExtra("news");
		
		titleText = (TextView) findViewById(R.id.textview_title);
		backButton = (Button) findViewById(R.id.button_back);
		titleText.setText(getIntent().getStringExtra("title"));
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		((TextView)findViewById(R.id.news_disp_title)).setText(news.getTitle());
		newsWebView = (WebView) findViewById(R.id.news_disp_content);
		
		String argvs = HttpUtil.createJsonStr("GetNewsDetail",
				"\"ArticleID\":\""+news.getArticleID()+"\",");
		HttpUtil.sendHttpRequest(HttpUtil.ADDRESS_NEWS_HANDLER, argvs,new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				JSONObject responseObject;
				
				try {
					responseObject = new JSONObject(response);

					if (responseObject.getString("error").equals("0")) {
						JSONArray jsonObjArray = responseObject.getJSONArray("data");
						JSONObject newsObject = (JSONObject) jsonObjArray.get(0);
						
						//图片适应宽度
						String sHead=   "<html><head><meta name=\"viewport\" content=\"width=device-width, " +
			                    "initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />"+
			                    "<style>img{max-width:100% !important;height:auto !important;}</style>"
			                    +"<style>body{max-width:100% !important;}</style>"+"</head><body>";
						html = sHead+newsObject.getString("Content_1")+"</body></html>";
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								newsWebView.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);  
							}
						});
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onError(Exception e) {
				
			}
		});
		
	}
}
