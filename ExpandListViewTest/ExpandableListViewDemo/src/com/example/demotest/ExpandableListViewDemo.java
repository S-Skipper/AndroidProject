package com.example.demotest;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.demotest.InfoDetailsAdapter.OnExpandListener;

/**
 * 可扩展的适配器BaseExpandableListAdapter---添加和删除
 * 
 * @author Yongke.Pan
 * @E-mail: itmadder@163.com
 * @version 创建时间：2015-5-24 下午4:52:18
 * @Des:
 */
public class ExpandableListViewDemo extends Activity {

	private ExpandableListView expandList;
	private InfoDetailsAdapter adapter;
	private List<String> group;
	private List<List<String>> child;

	// 初始化group child内容
	public void initialData() {
		// 初始化----Group组
		group = new ArrayList<String>();
		// 初始化----child子项
		child = new ArrayList<List<String>>();
		// 模拟数据，三个
		addInfo("组员One", new String[] { "one 1", "two 1", "three 1" });
		addInfo("组员Two", new String[] { "one 2", "two 2", "three 2" });
		addInfo("组员Three", new String[] { "one 3", "two 3", "three 3" });
	}

	private void addInfo(String p, String[] c) {

		// 给组添加一个元素
		group.add(p);
		// 给这个组的子项添加一个List表
		List<String> item = new ArrayList<String>();
		for (int i = 0; i < c.length; i++) {
			item.add(c[i]);
		}
		child.add(item);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		expandList = (ExpandableListView) findViewById(R.id.expandList);
		// 初始化各级元素
		initialData();
		// 适配器内容
		adapter = new InfoDetailsAdapter(this, group, child);
		adapter.setOnExpandListener(new OnExpandListener() {

			@Override
			public void onExpand(int groupPosition) {
				boolean isExpand = adapter.isExpand(groupPosition);
				if (isExpand)
					expandList.collapseGroup(groupPosition);
				else
					expandList.expandGroup(groupPosition, true);
			}
		});
		expandList.setAdapter(adapter);
		expandList.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i("TAG", "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_UP:
					Log.i("TAG", "ACTION_UP");
					break;
				case MotionEvent.ACTION_MOVE:
					Log.i("TAG", "ACTION_MOVE");
					break;
				}

				return false;
			}
		});
	}

	/*
	 * // 1、 点击某个分组Group expandList.setOnGroupClickListener(new
	 * OnGroupClickListener() {
	 * 
	 * @Override public boolean onGroupClick(ExpandableListView arg0, View arg1,
	 * int arg2, long arg3) {
	 * 
	 * // 删除
	 *//** =========================== */
	/*
	 * // dialogDeleteGroup(arg2 + "");
	 *//** =========================== */
	/*
	 * 
	 * // 添加 return false; } });
	 */
	/*
	 * // 2、点击分组里面的子项 expandList.setOnChildClickListener(new
	 * OnChildClickListener() {
	 * 
	 * @Override public boolean onChildClick(ExpandableListView arg0, View arg1,
	 * int arg2, int arg3, long arg4) {
	 * 
	 * // 删除
	 *//** =========================== */
	/*
	 * dialogDeleteChild(arg2, arg3);
	 *//** =========================== */
	/*
	 * 
	 * // 添加
	 *//** =========================== */
	/*

				*//** =========================== */
	/*
	 * return false; } }); }
	 */

	private EditText add_name;
	private EditText add_phone;
	private EditText add_sex;
	private EditText add_home;
	private Button add_ok;
	private Button add_no;
	private Button delete_ok;
	private Button delete_no;
	private Dialog dialogAdd;
	private Dialog dialogDelete;

	private void dialogDeleteChild(final int groupID, final int childID) {
		View viewDelete = this.getLayoutInflater().inflate(R.layout.delete,
				null);
		dialogDelete = new Dialog(this);
		dialogDelete.setContentView(viewDelete);
		dialogDelete.setTitle("删除指定成员");
		delete_ok = (Button) viewDelete.findViewById(R.id.delete_ok);
		delete_no = (Button) viewDelete.findViewById(R.id.delete_no);
		delete_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// group.remove(i);
				child.get(groupID).remove(childID);
				dialogDelete.dismiss();
				adapter.notifyDataSetChanged();
			}
		});
		delete_no.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogDelete.dismiss();
			}
		});
		dialogDelete.show();
	}

	private void dialogDeleteGroup(final String groupID) {
		View viewDelete = this.getLayoutInflater().inflate(R.layout.delete,
				null);
		dialogDelete = new Dialog(this);
		dialogDelete.setContentView(viewDelete);
		dialogDelete.setTitle("删除组");
		delete_ok = (Button) viewDelete.findViewById(R.id.delete_ok);
		delete_no = (Button) viewDelete.findViewById(R.id.delete_no);
		delete_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				group.remove(groupID);
				// child.remove(groupID);
				dialogDelete.dismiss();
				adapter.notifyDataSetChanged();
			}
		});
		delete_no.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogDelete.dismiss();
			}
		});
		dialogDelete.show();
	}

	private void createDialogAdd() {
		Log.i("", "createDialogAdd");
		View viewAdd = this.getLayoutInflater().inflate(R.layout.add, null);
		dialogAdd = new Dialog(this);
		dialogAdd.setContentView(viewAdd);
		dialogAdd.setTitle("输入新成员信息");
		add_name = (EditText) viewAdd.findViewById(R.id.add_name);
		add_phone = (EditText) viewAdd.findViewById(R.id.add_phone);
		add_sex = (EditText) viewAdd.findViewById(R.id.add_sex);
		add_home = (EditText) viewAdd.findViewById(R.id.add_home);
		add_ok = (Button) viewAdd.findViewById(R.id.add_ok);
		add_no = (Button) viewAdd.findViewById(R.id.add_no);
		add_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] data = { add_phone.getText().toString(),
						add_sex.getText().toString(),
						add_home.getText().toString() };
				addInfo(add_name.getText().toString(), data);
				dialogAdd.dismiss();
				adapter.notifyDataSetChanged();
			}
		});
		add_no.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogAdd.dismiss();
			}
		});
	}
}