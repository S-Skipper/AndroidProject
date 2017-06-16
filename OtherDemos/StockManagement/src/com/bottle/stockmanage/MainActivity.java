package com.bottle.stockmanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity{

	private Button btn1;
	private Button btn2;
	private Button btn3;
	private ListView listView;
	private SimpleAdapter adapter;
	private DBUtil dbUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn1 = (Button) findViewById(R.id.btn_all);
		btn2 = (Button) findViewById(R.id.btn_add);
		btn3 = (Button) findViewById(R.id.btn_delete);
		listView = (ListView) findViewById(R.id.listView);
		dbUtil = new DBUtil();
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideButton(true);
				setListView();
			}
		});

		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideButton(true);
				setAddDialog();
			}
		});

		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideButton(true);
				setDeleteDialog();
			}
		});
	}

	/**
	 * 设置弹出删除对话框
	 */
	private void setDeleteDialog() {
		
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog_delete);
		dialog.setTitle("输入想要删除的货物的编号");
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setAttributes(lp);

		final EditText cNoEditText = (EditText) dialog.findViewById(R.id.editText1);
		Button btnConfirm = (Button) dialog.findViewById(R.id.button1);
		Button btnCancel = (Button) dialog.findViewById(R.id.button2);

		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dbUtil.deleteCargoInfo(cNoEditText.getText().toString());
				dialog.dismiss();
				hideButton(false);
				Toast.makeText(MainActivity.this, "成功删除数据", Toast.LENGTH_SHORT).show();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				hideButton(false);
			}
		});
		
		dialog.show();
	}

	/**
	 * 设置弹出添加对话框
	 */
	private void setAddDialog() {

		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog_add);
		dialog.setTitle("输入添加的货物的信息");
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		dialogWindow.setAttributes(lp);

		final EditText cNameEditText = (EditText) dialog.findViewById(R.id.editText1);
		final EditText cNumEditText = (EditText) dialog.findViewById(R.id.editText2);
		Button btnConfirm = (Button) dialog.findViewById(R.id.button1);
		Button btnCancel = (Button) dialog.findViewById(R.id.button2);

		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				dbUtil.insertCargoInfo(cNameEditText.getText().toString(), cNumEditText.getText().toString());
				dialog.dismiss();
				hideButton(false);
				Toast.makeText(MainActivity.this, "成功添加数据", Toast.LENGTH_SHORT).show();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				hideButton(false);
			}
		});
		dialog.show();
	}

	/**
	 * 设置listView
	 */
	private void setListView() {

		listView.setVisibility(View.VISIBLE);

		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		list = dbUtil.getAllInfo();

		adapter = new SimpleAdapter(
				MainActivity.this, 
				list, 
				R.layout.adapter_item, 
				new String[] { "Cno", "Cname", "Cnum" }, 
				new int[] { R.id.txt_Cno, R.id.txt_Cname, R.id.txt_Cnum });

		listView.setAdapter(adapter);

	}

	/**
	 * 设置button的可见性
	 */
	private void hideButton(boolean result) {
		if (result) {
			btn1.setVisibility(View.GONE);
			btn2.setVisibility(View.GONE);
			btn3.setVisibility(View.GONE);
		} else {
			btn1.setVisibility(View.VISIBLE);
			btn2.setVisibility(View.VISIBLE);
			btn3.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * 返回按钮的重写
	 */
	@Override
	public void onBackPressed()
	{
		if (listView.getVisibility() == View.VISIBLE) {
			listView.setVisibility(View.GONE);
			hideButton(false);
		}else {
			MainActivity.this.finish();
		}
	}
}
