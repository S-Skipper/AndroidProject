package com.jacksen.drawerlayout.demo2;

import com.jacksen.drawerlayout.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SecondDemoActivity extends FragmentActivity implements
		OnItemClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mLv;
	private FragmentManager fragmentManager;
	private Fragment fragment1, fragment2, fragment3;
	private String[] str;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_second);

		init();
	}

	private void init() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout2);
		mDrawerLayout.setDrawerListener(new DrawerListener() {
			/**
			 * 当抽屉滑动状态改变的时候被调用
			 * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
			 * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
			 */
			@Override
			public void onDrawerStateChanged(int arg0) {
				Log.i("drawer", "drawer的状态：" + arg0);
			}
			/**
			 * 当抽屉被滑动的时候调用此方法
			 * arg1 表示 滑动的幅度（0-1）
			 */
			@Override
			public void onDrawerSlide(View arg0, float arg1) {
				Log.i("drawer", arg1 + "");
			}
			/**
			 * 当一个抽屉被完全打开的时候被调用
			 */
			@Override
			public void onDrawerOpened(View arg0) {
				Log.i("drawer", "抽屉被完全打开了！");
			}
			/**
			 * 当一个抽屉完全关闭的时候调用此方法
			 */
			@Override
			public void onDrawerClosed(View arg0) {
				Log.i("drawer", "抽屉被完全关闭了！");
			}
		});
		
		/**
		 * 也可以使用DrawerListener的子类SimpleDrawerListener, 
		 * 或者是ActionBarDrawerToggle这个子类(详见FirstDemoActivity)
		 */
		mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		});
		
		
		mLv = (ListView) findViewById(R.id.id_lv);
		str = new String[] { "item1", "item2", "item3"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, str);
		mLv.setAdapter(adapter);
		mLv.setOnItemClickListener(this);

		fragmentManager = getSupportFragmentManager();
		fragment1 = new Fragment1();
		fragment2 = new Fragment2();
		fragment3 = new Fragment3();
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.id_framelayout2, fragment1).commit();
			break;
		case 1:
			fragmentManager.beginTransaction()
					.replace(R.id.id_framelayout2, fragment2).commit();
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.id_framelayout2, fragment3).commit();
			break;
		}
		setTitle(str[position]);
		mDrawerLayout.closeDrawers();
	}

}
