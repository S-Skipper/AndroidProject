package com.chemlab.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.chemlab.R;
import com.chemlab.activity.GameActivity;
import com.chemlab.activity.MipcaActivityCapture;
import com.chemlab.activity.SignInActivity;
import com.chemlab.activity.ThoughtActivity;
import com.chemlab.activity.TodayActivity;
import com.chemlab.activity.WebViewActivity;
import com.chemlab.adapter.MyGridAdapter;
import com.chemlab.systems.CourseSystemActivity;
import com.chemlab.systems.DrugSystemActivity;
import com.chemlab.systems.EquipmentSystemActivity;
import com.chemlab.systems.LabSystemActivity;
import com.chemlab.systems.WorkSystemActivity;
import com.chemlab.util.HttpUtil;
import com.chemlab.view.MyGridView;

public class SystemFragment extends Fragment {

	private View view;

	private MyGridView system_gridview;
	private MyGridView assist_gridview;
	private MyGridView ext_gridview;
	
	private String linkPTable = "file:///android_asset/www/ptable/index.html";

	// 学生助理
	private String[] mMenuTexts1 = new String[] { 
			"签到/签退", "留言板", "今日安排 ",
			"工作情况", "待定", " " };
	private int[] mMenuImgs1 = new int[] { 
			R.drawable.v1_app1,
			R.drawable.v1_app2, 
			R.drawable.v1_app3, 
			R.drawable.v1_app4,
			R.drawable.v1_app51, 
			R.drawable.transparent };

	// 子系统
	private String[] mMenuTexts2 = new String[] { 
			"药品管理 ", "仪器管理", "实验管理", "课程管理", "", " " };
	//开放性实验

	private int[] mMenuImgs2 = new int[] { 
			R.drawable.v2_app1,
			R.drawable.v2_app2, 
			R.drawable.v2_app3, 
			R.drawable.v2_app4,
			R.drawable.transparent, 
			R.drawable.transparent };

	// 其他探索
	private String[] mMenuTexts3 = new String[] { 
			"网页版", "扫一扫", "元素周期表", "休闲", " ", " "};
	private int[] mMenuImgs3 = new int[] { 
			R.drawable.v3_app1,
			R.drawable.v3_app2, 
			R.drawable.v3_app3,
			R.drawable.v3_app4,
			R.drawable.transparent,
			R.drawable.transparent };

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_functions, container, false);

		initGridMenuView();

		return view;
	}

	private void initGridMenuView() {
		assist_gridview = (MyGridView) view.findViewById(R.id.assist_gridview);
		assist_gridview.setAdapter(new MyGridAdapter(getActivity(), mMenuImgs1,mMenuTexts1));

		system_gridview = (MyGridView) view.findViewById(R.id.system_gridview);
		system_gridview.setAdapter(new MyGridAdapter(getActivity(), mMenuImgs2,mMenuTexts2));

		ext_gridview = (MyGridView) view.findViewById(R.id.adventure_gridview);
		ext_gridview.setAdapter(new MyGridAdapter(getActivity(), mMenuImgs3,mMenuTexts3));

		assist_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parant, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startAction(SignInActivity.class);
					break;
				case 1:
					startAction(ThoughtActivity.class);
					break;
				case 2:
					startAction(TodayActivity.class);
					break;
				case 3:
					startAction(WorkSystemActivity.class);
					break;
				case 4:
					break;
				default:
					break;
				}
			}
		});

		system_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parant, View view,
					int position, long id) {
				switch (position) {
				case 0:
					startAction(DrugSystemActivity.class);
					break;
				case 1:
					startAction(EquipmentSystemActivity.class);
					break;
				case 2:
					startAction(LabSystemActivity.class);
					break;
				case 3:
					startAction(CourseSystemActivity.class);
					break;
				case 4:
					break;
				default:
					break;
				}
			}
		});
		
		ext_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parant, View view,
					int position, long id) {
				switch (position) {
				case 0:
					WebViewActivity.actionStart(getActivity(), "Chemlab",HttpUtil.ADDRESS_HOME);
					/*Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(HttpUtil.ADDRESS_HOME));
					startActivity(intent);*/
					break;
				case 1:
					startAction(MipcaActivityCapture.class);
					break;
				case 2:
					WebViewActivity.actionStart(getActivity(), "元素周期表",linkPTable);
					break;
				case 3:
					//startAction(CircleMenuActivity.class);
					startAction(GameActivity.class);
					break;
				default:
					break;
				}
			}
		});
	}

	private void startAction(Class<?> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
	}
}
