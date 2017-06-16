package com.zxxk.kg.listview_test;

import java.util.LinkedList;

import com.zxxk.kg.listview_test.listview.MyListViewPullDownAndUp;
import com.zxxk.kg.listview_test.listview.MyListViewPullDownAndUp.RefreshListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewPull_DownAndUPActivity extends Activity{

		   private MyListViewPullDownAndUp lv;
		   private LinkedList<String> data;
		   private MyAdapter adapter; 
		   int i=1;
		   Handler handler=new Handler();
		   @Override
		   protected void onCreate(Bundle savedInstanceState) {
		       super.onCreate(savedInstanceState);
		       setContentView(R.layout.list_pull_down_up);
		       
		       data=new LinkedList<String>();
		       data.add("aaaaaaaa");
		       data.add("bbbbbbbbbbb");
		       data.add("ccccc");
		       data.add("dddddddd");
		       data.add("eeeeeeeeeeeee");
		       data.add("ffffffffffffffff");
		       data.add("gggg");
		       data.add("hhhhhhhhhhhhhhhh");
		       data.add("11111111111111111111111");
		       data.add("222222222222222222222222");
		       data.add("333333333333333333333333");
		       data.add("4444444444444444444444");
		       data.add("12111111111111111");
		       data.add("33333333333111111111111111");
		       data.add("11111111111111111111122222222222222");
		       data.add("aaaaaaaa");
		       data.add("bbbbbbbbbbb");
		       data.add("ccccc");
		       data.add("dddddddd");
		       data.add("eeeeeeeeeeeee");
		       data.add("ffffffffffffffff");
		       data.add("gggg");
		       data.add("hhhhhhhhhhhhhhhh");
		       data.add("11111111111111111111111");
		       data.add("222222222222222222222222");
		       data.add("333333333333333333333333");
		       data.add("4444444444444444444444");
		       data.add("12111111111111111");
		       data.add("33333333333111111111111111");
		       data.add("11111111111111111111122222222222222");
		       lv=(MyListViewPullDownAndUp) findViewById(R.id.lv);
		       adapter=new MyAdapter();
		       lv.setAdapter(adapter); 
		       System.out.println("lv.getFirstVisiblePosition()="+lv.getFirstVisiblePosition());
		       System.out.println("lv.getCount()="+lv.getCount()+",,,adapter.getCount()="+adapter.getCount());
		       System.out.println("lv.getLastVisiblePosition()="+lv.getLastVisiblePosition());
		       lv.setRefreshListener(new MyRefreshListener());
		   } 
		   class MyRefreshListener implements RefreshListener{ 
		       //处理下拉刷新
		           @Override
		           public void pullDownRefresh() { 
		               new Thread(new Runnable() { 
		                   @Override
		                   public void run() {
		                       SystemClock.sleep(2000);
		                       data.addFirst(i+++"new下拉更新data……………………"); 
		                       
		                       handler.post(new Runnable() { 
		                           @Override
		                           public void run() {
		                               adapter.notifyDataSetChanged();
		                               lv.onPulldownRefreshComplete();
		                               Toast.makeText(getApplicationContext(), "数据添加完成",Toast.LENGTH_LONG).show();
		                               System.out.println(lv.getLastVisiblePosition()+"======="+adapter.getCount());
		                           }
		                       }); 
		                   }
		               }).start();
		           }
		   //处理上拉刷新
		           @Override
		           public void pullUpRefresh() {
		               new Thread(new Runnable() { 
		                   @Override
		                   public void run() {
		                       SystemClock.sleep(2000);
		                       data.addLast(i+++"new上拉更新data……………………"); 
		                       
		                       handler.post(new Runnable() { 
		                           @Override
		                           public void run() {
		                               adapter.notifyDataSetChanged();
		                               lv.onPullupRefreshComplete();
		                               Toast.makeText(getApplicationContext(), "数据添加完成",Toast.LENGTH_LONG).show();
		                               System.out.println(lv.getLastVisiblePosition()+"======="+adapter.getCount());
		                           }
		                       }); 
		                   }
		               }).start();
		           }
		       } 
		 class MyAdapter extends BaseAdapter{
		
		       @Override
		       public int getCount() {
		           // TODO Auto-generated method stub
		           return data.size();
		       }
		
		       @Override
		       public Object getItem(int position) {
		           // TODO Auto-generated method stub
		           return position;
		       }
		
		       @Override
		       public long getItemId(int position) {
		           // TODO Auto-generated method stub
		           return position;
		       }
		
		       @Override
		       public View getView(int position, View convertView, ViewGroup parent) {
		   TextView tv=new TextView(ListViewPull_DownAndUPActivity.this);
		   tv.setTextColor(Color.WHITE);
		   tv.setText(data.get(position));
		           return tv;
		       }
		     
		 }
		
}
