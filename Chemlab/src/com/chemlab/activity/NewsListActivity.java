package com.chemlab.activity;

import java.io.Serializable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.adapter.NewsAdapter;
import com.chemlab.managers.JsonManager;
import com.chemlab.objs.News;
import com.chemlab.util.HttpCallbackListener;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.LogUtil;
import com.chemlab.view.RefreshableView;
import com.chemlab.view.RefreshableView.PullToRefreshListener;

public class NewsListActivity extends Activity {

	private TextView titleText;
	private Button backButton;

	private NewsAdapter newsAdapter;
	private ListView newsListView;
	RefreshableView refreshableView;

	private List<News> list;
	private String titleString;

	public static void actionStart(Context context, String title,
			List<News> newslist) {
		Intent intent = new Intent(context, NewsListActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("news_list", (Serializable) newslist);
		context.startActivity(intent);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_list);

		titleString = getIntent().getStringExtra("title");
		list = (List<News>) getIntent().getSerializableExtra("news_list");
		newsAdapter = new NewsAdapter(NewsListActivity.this,
				R.layout.item_news, list);

		titleText = (TextView) findViewById(R.id.textview_title);
		backButton = (Button) findViewById(R.id.button_back);
		titleText.setText(titleString);

		refreshableView = (RefreshableView) findViewById(R.id.refreshable_news);
		newsListView = (ListView) findViewById(R.id.list_news);
		newsListView.setAdapter(newsAdapter);

		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				News news = list.get(pos);
				NewsDispActivity.actionStart(NewsListActivity.this,
						titleString, news);
			}

		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				finish();
			}
		});
		
		refreshableView.setShowType(RefreshableView.TYPE_NO_UPDATE_AT);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					updateNewsList();
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);

	}

	private void updateNewsList() {
		String news_type = "1";
		if (titleString.equals("新闻")) {
			news_type = "1";
		}else if (titleString.equals("公告")) {
			news_type = "2";
		}
		
		String argvs = HttpUtil.createJsonStr("GetNewsLate",
				"\"num\":\"9999999\",\"news_type\":\""+news_type+"\",");
		//LogUtil.d("NewsListActivity", argvs);
		HttpUtil.sendHttpRequest(HttpUtil.ADDRESS_NEWS_HANDLER, argvs,
				new HttpCallbackListener() {

					@Override
					public void onFinish(String response) {
						LogUtil.d("NewsListActivity", response);
						JSONObject responseObject;
						try {
							responseObject = new JSONObject(response);

							if (responseObject.getString("error").equals("0")) {
								JSONArray jsonObjArray = responseObject
										.getJSONArray("data");
								list.clear();
								list.addAll(JsonManager.getNewsArray(jsonObjArray));
								
								if (list!=null && !list.isEmpty()) {
									runOnUiThread(new Runnable() {
										public void run() {
											newsAdapter.notifyDataSetChanged();
										}
									});
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Exception e) {
						LogUtil.d("JsonManager", "error");
					}
				});
	}
}
