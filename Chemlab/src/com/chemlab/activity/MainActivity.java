package com.chemlab.activity;

import com.chemlab.R;
import com.chemlab.about.FeedbackActivity;
import com.chemlab.fragment.ContactFragment;
import com.chemlab.fragment.PersonalFragment;
import com.chemlab.fragment.SystemFragment;
import com.chemlab.fragment.NewsFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	//TitleBar items
	private TextView titleText;
	private Button titleMenu;
	
	private PopupWindow popupWindow;
	
	private RadioGroup mainGroup;
	private FragmentManager fragmentManager;
	private NewsFragment newsFragment;
	private ContactFragment contactFragment;
	private SystemFragment functionFragment;
	private PersonalFragment personalFragment;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		titleText = (TextView) findViewById(R.id.title_text);
		titleMenu = (Button) findViewById(R.id.title_menu);
		titleText.setText("首页");

		mainGroup = (RadioGroup) findViewById(R.id.main_tab);
		mainGroup.setOnCheckedChangeListener(this);
		fragmentManager = getFragmentManager();
		newsFragment = (NewsFragment) fragmentManager
				.findFragmentById(R.id.news_fragment);
		contactFragment = (ContactFragment) fragmentManager
				.findFragmentById(R.id.contact_fragment);
		functionFragment = (SystemFragment) fragmentManager
				.findFragmentById(R.id.function_fragment);
		personalFragment = (PersonalFragment) fragmentManager
				.findFragmentById(R.id.personal_fragment);
		showFragment(newsFragment);

		View view = LayoutInflater.from(this).inflate(R.layout.layout_popmenu, null);
		view.measure(0, 0);
		popupWindow = new PopupWindow(view, view.getMeasuredWidth(),view.getMeasuredHeight());
		
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		
		titleMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//popupWindow.showAsDropDown(v,v.getWidth()-200,3);
				Rect rectangle= new Rect();  
		        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);  
		        //高度为rectangle.top-0仍为rectangle.top    
				popupWindow.showAtLocation(v, 
						Gravity.NO_GRAVITY, 
						((View)v.getParent()).getWidth()-popupWindow.getWidth()-20,
						((View)v.getParent()).getHeight()+rectangle.top);
				// 使其聚集
				//LogUtil.d("Tag", popupWindow.getWidth()+"");
				popupWindow.setFocusable(true);
				//刷新状态（必须刷新否则无效）
				popupWindow.update();
			}
		});
		
		view.findViewById(R.id.pop_search).setOnClickListener(listener);
		view.findViewById(R.id.pop_scan).setOnClickListener(listener);
		view.findViewById(R.id.pop_feedback).setOnClickListener(listener);
	}
    private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			popupWindow.dismiss();
			switch (v.getId()) {
			case R.id.pop_search:
				startAction(SearchSelectActivity.class);
				break;
			case R.id.pop_scan:
				startAction(MipcaActivityCapture.class);
				break;
			case R.id.pop_feedback:
				startAction(FeedbackActivity.class);
				break;
			default:
				break;
			}
		}
	};
	
	private void startAction(Class<?> clazz) {
		Intent intent = new Intent(MainActivity.this, clazz);
		startActivity(intent);
	}
	
	private void showFragment(Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// transaction.replace(R.id.content_layout, fragment);
		transaction.hide(newsFragment)
		           .hide(contactFragment)
		           .hide(functionFragment)
		           .hide(personalFragment);

		transaction.show(fragment).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		switch (id) {
		case R.id.radio_button1:
			showFragment(newsFragment);
			titleText.setText("首页");
			break;
		case R.id.radio_button2:
			showFragment(contactFragment);
			titleText.setText("联系人");
			break;
		case R.id.radio_button3:
			showFragment(functionFragment);
			titleText.setText("系统功能");
			break;
		case R.id.radio_button4:
			showFragment(personalFragment);
			titleText.setText("个人中心");
			break;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_quit:
			finish();
			break;
		default:
			break;
		}

		return true;
	}
}
