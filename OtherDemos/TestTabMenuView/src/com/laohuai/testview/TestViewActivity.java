package com.laohuai.testview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.laohuai.testview.TravelView.OnTravelListener;
import com.laohuai.testview.views.ViewHolder0;
import com.laohuai.testview.views.ViewHolder1;

public class TestViewActivity extends Activity {

	// 保存具体展示的内容,当然这里您也可以使用其他的组件，像ViewFlipper，ViewAnimator等，这样可以在两个view切换时候加一个动画效果，使得界面切换之间更加生动
	private LinearLayout contentLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TravelView tv = (TravelView) findViewById(R.id.tabLayout);
		contentLayout = (LinearLayout) findViewById(R.id.contentLayout);

		tv.initView(R.drawable.bg, R.drawable.cover, new int[] {
				R.drawable.btn_1, R.drawable.btn_2, R.drawable.btn_3,
				R.drawable.btn_4, R.drawable.btn_5, R.drawable.btn_6,
				R.drawable.btn_7, R.drawable.btn_8, R.drawable.btn_9,
				R.drawable.btn_10 }, new String[] { "111", "222", "333", "444",
				"111", "222", "333", "444", "999", "000" }, new boolean[] {
				true, true, true, true, true, true, true, true, true, true });

		tv.setOnTravelListener(new OnTravelListener() {
			public void onTravel(final int index) {

				// 如果不想这样写的话，也可以在TravelView中加一个Handler参数，在initView时候new一个Handler以参数的形式传递过去，同时在TravelView的coverRun中，将执行onTravel方法处用handler包一下
				runOnUiThread(new Runnable() {
					View view = null;

					public void run() {
						switch (index) {
						case 0:
							view = new ViewHolder0(getLayoutInflater())
									.getView();
							break;
						case 1:
							view = new ViewHolder1(getLayoutInflater())
									.getView();
							break;
						}

						if (view != null) {
							contentLayout.removeAllViews();
							contentLayout.addView(view,
									new LinearLayout.LayoutParams(
											LayoutParams.FILL_PARENT,
											LayoutParams.FILL_PARENT));
						}

					}
				});

			}
		});
		tv.moveToAndFollowWith(5);
	}
}