package com.dtt.fragment.news;

import java.util.ArrayList;
import java.util.List;

import com.dtt.fragment.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NewsTitleFragment extends Fragment implements OnItemClickListener {

	private ListView newsTitleListView;
	private List<News> newsList;
	private NewsAdapter adapter;
	private boolean isTwoPane;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		newsList = getNews();
		adapter = new NewsAdapter(activity, R.layout.news_item, newsList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.news_title_fragment, container,
				false);
		newsTitleListView = (ListView) view.findViewById(R.id.news_title_list);
		newsTitleListView.setAdapter(adapter);
		newsTitleListView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (getActivity().findViewById(R.id.news_content_layout) != null) {
			isTwoPane = true;
		} else {
			isTwoPane = false;
		}
	}

	private List<News> getNews() {
		List<News> newsList = new ArrayList<News>();
		News news1 = new News();
		news1.setTitle("News 1: Test the ellipse whether be the right!");
		news1.setContent("111111111111111111111111111111111111111111111111");
		newsList.add(news1);

		News news2 = new News();
		news2.setTitle("News 2: Test the ellipse whether be the right!");
		news2.setContent("22222222222222222222222222222222222222222222222222");
		newsList.add(news2);
		return newsList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		News news = newsList.get(position);

		if (isTwoPane) {
			NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
					.findFragmentById(R.id.news_content_fragment);
			newsContentFragment.refersh(news.getTitle(), news.getContent());
		} else {
			NewsContentActivity.actionStart(getActivity(), news.getTitle(),
					news.getContent());
		}
	}

}
