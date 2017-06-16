package com.dtt.expandlistview;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ExpandableListView exListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		exListView = (ExpandableListView) findViewById(R.id.ext_list);
		exListView.setAdapter(adapter);
	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
		// 设置组视图的图片
		int[] logos = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };
		// 设置组视图的显示文字
		private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
		// 子视图显示文字
		private String[][] generals = new String[][] {
				{ "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
				{ "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
				{ "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }

		};
		// 子视图图片
		public int[][] generallogos = new int[][] {
				{ R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
						R.drawable.ic_launcher, R.drawable.ic_launcher,
						R.drawable.ic_launcher },
				{ R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
						R.drawable.ic_launcher, R.drawable.ic_launcher,
						R.drawable.ic_launcher },
				{ R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
						R.drawable.ic_launcher, R.drawable.ic_launcher } };

		// 自己定义一个获得文字信息的方法
		TextView getTextView() {
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 64);
			TextView textView = new TextView(MainActivity.this);
			textView.setLayoutParams(lp);
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setPadding(36, 0, 0, 0);
			textView.setTextSize(20);
			textView.setTextColor(Color.BLACK);
			return textView;
		}

		// 重写ExpandableListAdapter中的各个方法
		@Override
		public int getGroupCount() {
			// 
			return generalsTypes.length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return generalsTypes[groupPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return generals[groupPosition].length;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return generals[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout ll = new LinearLayout(MainActivity.this);
			ll.setOrientation(0);
			ImageView logo = new ImageView(MainActivity.this);
			logo.setImageResource(logos[groupPosition]);
			logo.setPadding(50, 0, 0, 0);
			ll.addView(logo);
			TextView textView = getTextView();
			textView.setTextColor(Color.BLACK);
			textView.setText(getGroup(groupPosition).toString());
			ll.addView(textView);

			return ll;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout ll = new LinearLayout(MainActivity.this);
			ll.setOrientation(0);
			ImageView generallogo = new ImageView(MainActivity.this);
			generallogo
					.setImageResource(generallogos[groupPosition][childPosition]);
			ll.addView(generallogo);
			TextView textView = getTextView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			ll.addView(textView);
			return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
