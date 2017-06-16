package com.dtt.chemlab.activity;

import com.dtt.chemlab.R;
import com.dtt.chemlab.fragment.ContactFragment;
import com.dtt.chemlab.fragment.FunctionFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private TextView titleText;
	private Button titleMenu;
	
	private RadioGroup mainGroup;
	private FragmentManager fragmentManager;
	private ContactFragment contactFragment;
	private FunctionFragment functionFragment;

	@Override
	protected void setContents() {
		// super.setContents();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		content = findViewById(R.id.content_son);
		menu = findViewById(R.id.menu);
	}
	
	@Override
	protected void initView() {
		super.initView();

		titleText = (TextView) findViewById(R.id.title_text);
		titleMenu = (Button) findViewById(R.id.title_menu);
		titleText.setText("通讯录");
		
		mainGroup = (RadioGroup) findViewById(R.id.main_tab);
		mainGroup.setOnCheckedChangeListener(this);
		fragmentManager = getFragmentManager();
		contactFragment = (ContactFragment) fragmentManager.findFragmentById(R.id.contact_fragment);
		functionFragment = (FunctionFragment) fragmentManager.findFragmentById(R.id.function_fragment);
		showFragment(contactFragment);
	}

	private void showFragment(Fragment fragment) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//transaction.replace(R.id.content_layout, fragment);
		transaction.hide(contactFragment).hide(functionFragment);
		
		transaction.show(fragment).commit();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int id) {
		switch (id) {
		case R.id.radio_button1:
			showFragment(contactFragment);
			titleText.setText("通讯录");
			break;
		case R.id.radio_button2:
			showFragment(functionFragment);
			titleText.setText("系统功能");
			break;
		}
	}
}
