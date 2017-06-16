package com.listviewdemo_expandablelistview;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
	private ExpandableListView listView;
	private List<String> group;
	private List<List<String>> child;
	private MyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ExpandableListView) findViewById(R.id.expandableListView);
		/**
		 * 初始化数据 
		 */
		initData();
		adapter = new MyAdapter(this,group,child);
		listView.setAdapter(adapter);
		
	}

	private void initData() {
		group = new ArrayList<String>();
		child = new ArrayList<List<String>>();
		addInfo("北京",new String[]{"朝阳","海淀","东城区","西城区"});
		addInfo("河北", new String[]{"邯郸","石家庄","邢台"});
		addInfo("广东", new String[]{"广州","深圳","珠海"});
	}
	
	/**
	 * 添加数据信息
	 * @param g
	 * @param c
	 */
	private void addInfo(String g,String[] c) {
		group.add(g);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			list.add(c[i]);
		}
		child.add(list);
	}

}
