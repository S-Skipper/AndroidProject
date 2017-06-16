package com.dtt.chemlab.fragment;

import com.dtt.chemlab.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dtt.chemlab.systems.AssistentSystemActivity;
import com.dtt.chemlab.systems.DrugSystemActivity;
import com.dtt.chemlab.util.MyApplication;
import com.dtt.chemlab.view.CircleMenuLayout;
import com.dtt.chemlab.view.CircleMenuLayout.OnMenuItemClickListener;

public class SystemFragment extends Fragment {

	private View view;
	
	private CircleMenuLayout mCircleMenuLayout;
	private String[] mItemTexts = new String[] {  "开放性实验", "药品管理 ", "出入库", 
			"实验器材", "学生助理", "课程管理"};
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// return super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_system_function, container, false);
		
		mCircleMenuLayout = (CircleMenuLayout) view.findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		
		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{
			
			@Override
			public void itemClick(View view, int pos)
			{
				Toast.makeText(MyApplication.getContext(), mItemTexts[pos],
						Toast.LENGTH_SHORT).show();
				
				switch (pos) {
				case 0:
					break;
				case 1:
					startAction(DrugSystemActivity.class);
					break;
				case 4:
					startAction(AssistentSystemActivity.class);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(MyApplication.getContext(),
						"必须登录才能使用完整功能!",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		
		return view;
	}
	
	private void startAction(Class<?> clazz){
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
	}
}
