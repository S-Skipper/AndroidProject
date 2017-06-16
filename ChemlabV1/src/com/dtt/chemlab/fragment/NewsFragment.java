package com.dtt.chemlab.fragment;

import java.util.ArrayList;
import java.util.List;

import com.dtt.chemlab.R;
import com.dtt.chemlab.activity.SearchActivity;
import com.dtt.chemlab.managers.NewsManager;
import com.dtt.chemlab.objs.News;
import com.dtt.chemlab.util.HttpUtil;
import com.dtt.chemlab.view.BannerView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFragment extends Fragment {

	private View view;

	//private EditText drugNameText;
	//private Button drugSearchButton;

	private ListView newsList;
	private ArrayAdapter<String> adapter;
	private List<String> newsTitle;
	private List<News> list;

	private ImageView newsUpdate;
	private TextView viewMoreNews;
	private TextView viewOtherNews;

	private ImageView noticeUpdate;
	private TextView viewLastNotice;
	private TextView viewAllNotice;

	private TextView noticeBodyText;

	private BannerView bannerImg;

	private int newsIndex;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!HttpUtil.isNetworkAvailable()) {
			Toast.makeText(getActivity(), "Network is unavailable!",
					Toast.LENGTH_SHORT).show();
		}

		if (newsTitle == null) {
			newsTitle = new ArrayList<String>();
			newsTitle.add("正在加载中...");
			newsTitle.add("正在加载中...");
			newsTitle.add("正在加载中...");
			newsIndex = 3;
		}

		adapter = new ArrayAdapter<String>(this.getActivity(),
				R.layout.item_news_title, newsTitle);

		updateNews();
	}

	private void updateNews() {
		AsyncTask.execute(new Runnable() {

			@Override
			public void run() {
				// newsTitle.clear();
				list = NewsManager.getNewsArray(NewsManager.getNews("1"));
				
				if (list != null && !list.isEmpty()) {
					newsTitle.clear();
					newsTitle.add(list.get(0).getTitle());
					newsTitle.add(list.get(1).getTitle());
					newsTitle.add(list.get(2).getTitle());
					newsIndex = 3;
					adapter.notifyDataSetChanged();
				}
			}
		});
	}
	
	private void updateNotice() {
		Toast.makeText(getActivity(), "noticeUpdate", Toast.LENGTH_LONG).show();
		AsyncTask.execute(new Runnable() {

			@Override
			public void run() {
				// newsTitle.clear();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_news, container, false);
		//drugNameText = (EditText) view.findViewById(R.id.news_drug_name);
		//drugSearchButton = (Button) view.findViewById(R.id.news_bt_search);
		
		view.findViewById(R.id.news_drug_name).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
		view.findViewById(R.id.news_bt_search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
			}
		});
		
		newsUpdate = (ImageView) view.findViewById(R.id.news_update);
		viewMoreNews = (TextView) view.findViewById(R.id.view_all_news);
		viewOtherNews = (TextView) view.findViewById(R.id.view_other_news);

		noticeUpdate = (ImageView) view.findViewById(R.id.notice_update);
		viewLastNotice = (TextView) view.findViewById(R.id.view_last_notice);
		viewAllNotice = (TextView) view.findViewById(R.id.view_all_notice);
		noticeBodyText = (TextView) view.findViewById(R.id.notice_body);

		newsList = (ListView) view.findViewById(R.id.news_title);
		newsList.setAdapter(adapter);

		bannerImg = (BannerView) view.findViewById(R.id.mainBanner);
		List<String> imgs = new ArrayList<String>();

		/*
		 * imgs.add(0,"http://211.66.136.32/img/banner1.jpg");
		 * imgs.add(1,"http://211.66.136.32/img/banner2.jpg");
		 * imgs.add(2,"http://211.66.136.32/img/banner3.jpg");
		 * imgs.add(3,"http://211.66.136.32/img/banner4.jpg");
		 */

		imgs.add(0, "drawable://" + R.drawable.banner1);
		imgs.add(1, "drawable://" + R.drawable.banner2);
		imgs.add(2, "drawable://" + R.drawable.banner3);
		imgs.add(3, "drawable://" + R.drawable.banner4);
		bannerImg.setImageUris(imgs);

		newsList.setOnItemClickListener(listener);

		
		//drugNameText.setOnClickListener(searchListener);
		//drugSearchButton.setOnClickListener(searchListener);

		newsUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				updateNews();
			}
		});

		noticeUpdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateNotice();
			}
		});

		viewMoreNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "More News", Toast.LENGTH_LONG)
						.show();
			}
		});

		viewOtherNews.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (list != null && !list.isEmpty()) {
					newsTitle.clear();

					for (int i = 0; i < 3; i++) {
						if (newsIndex >= list.size()) {
							newsIndex = 0;
						}
						newsTitle.add(list.get(newsIndex).getTitle());
						newsIndex++;
					}
					adapter.notifyDataSetChanged();
				}
			}
		});

		viewLastNotice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				noticeBodyText.setText("上一条通知");
			}
		});

		viewAllNotice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "viewAllNotice", Toast.LENGTH_SHORT)
				.show();
			}
		});

		return view;
	}
	
	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> content, View view,
				int position, long id) {
			
			if (list!=null&&!list.isEmpty()) {
				Toast.makeText(getActivity(),
						list.get(position - 3 + newsIndex).getTitle(),
						Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(getActivity(),
						"News list is empty!",
						Toast.LENGTH_SHORT).show();
			}
		}

	};
}
