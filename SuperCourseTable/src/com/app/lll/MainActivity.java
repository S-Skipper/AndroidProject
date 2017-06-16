package com.app.lll;

import com.app.lll.bean.CustomDate;
import com.app.lll.fragment.CourseTable;
import com.app.lll.util.DateUtil;
import com.art.lll.R;


import butterknife.Bind;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;

/**
 * 
 * @author liulongling
 *
 */
public class MainActivity extends FragmentActivity {

	protected static final String TAG = "MainActivity";

	/**月视图 or 周视图*/
	@Bind(R.id.fl_view)
	FrameLayout mFrameLayout;
	
	private CustomDate mClickDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		CourseTable table = new CourseTable();
		table.setmContext(this);
		//设置选中日期
		if(mClickDate!=null){
			CustomDate mShowDate = mClickDate;
			int curMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month);
			//获取周日
			if (mShowDate.day - mShowDate.week+7 > curMonthDays){
				if (mShowDate.month == 12) {
					mShowDate.month = 1;
					mShowDate.year += 1;
				} else {
					mShowDate.month += 1;
				}
				mShowDate.day = (mShowDate.day- mShowDate.week-1)+7-curMonthDays;
			}else{
				mShowDate.day = mShowDate.day - mShowDate.week+7;
			}		
			table.setShowDate(mShowDate);
		}

		this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_view, table).commit();
	}

}
