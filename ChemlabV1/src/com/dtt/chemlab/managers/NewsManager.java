package com.dtt.chemlab.managers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.dtt.chemlab.objs.News;
import com.dtt.chemlab.util.HttpUtil;

public class NewsManager {

	public static final String address = "http://211.66.136.32/WebService_News.asmx";

	public NewsManager() {
	}

	public static String getNews(String type) {
		return HttpUtil.requestWebservice(address, "GetNews",
				new String[] { "type" }, new String[] { type });
	}

	public static String getDetailNews(String num) {
		return HttpUtil.requestWebservice(address, "GetNewsDetail",
				new String[] { "num" }, new String[] { num });
	}

	public static List<News> getNewsArray(String jsonData) {
		
		if (jsonData==null) {
			return null;
		}
		
		List<News> list = new ArrayList<News>();

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			News news;
			for (int i = 0; i < jsonObject.length(); i++) {
				JSONObject newsInfo = jsonObject.getJSONObject("" + i);
				Log.i("Tag", newsInfo.toString());
				news = new News();

				news.setArticleID(newsInfo.getString("ArticleID"));
				news.setTitle(newsInfo.getString("Title"));
				news.setContent_1(newsInfo.getString("Content_1"));
				news.setDateTime(newsInfo.getString("DateTime"));
				news.setHits(newsInfo.getString("Hits"));
				news.setMember_Name(newsInfo.getString("Member_Name"));
				news.setType(newsInfo.getString("type"));
				news.setInfor(newsInfo.getString("infor"));

				list.add(news);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
}
