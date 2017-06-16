package com.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.chemlab.R;
import com.chemlab.util.MyApplication;
import com.chemlab.view.CircleMenuLayout;
import com.chemlab.view.CircleMenuLayout.OnMenuItemClickListener;

public class CircleMenuActivity extends Activity {

	private CircleMenuLayout mCircleMenuLayout;

	// 子系统
	private String[] mMenuTexts = new String[] { "选项1", "选项2","选项3",
			"选项4", "选项5"};
	private int[] mMenuImgs = new int[] { R.drawable.v2_app1,
			R.drawable.v2_app2, R.drawable.v1_app3, R.drawable.v2_app3,
			R.drawable.v2_app4 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_circle_menu);
		initCircleMenuView();
	}

	void initCircleMenuView() {
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mMenuImgs, mMenuTexts);

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public void itemClick(View view, int pos) {
						Toast.makeText(MyApplication.getContext(),
								mMenuTexts[pos], Toast.LENGTH_SHORT).show();

						switch (pos) {
						case 0:
							break;
						case 1:
							break;
						case 4:
							break;
						default:
							break;
						}
					}

					@Override
					public void itemCenterClick(View view) {
						Toast.makeText(MyApplication.getContext(),
								"必须登录才能使用完整功能!", Toast.LENGTH_SHORT).show();

					}
				});
	}

	private void startAction(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

}
