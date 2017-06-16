package com.chemlab.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.objs.Contact;
import com.chemlab.util.HttpUtil;
import com.chemlab.util.MyApplication;
import com.chemlab.view.InfoDispView;

public class UserActivity extends Activity implements OnClickListener {

	public static final int CODE_PHONE = 1;
	public static final int CODE_PHONESHORT = 2;
	public static final int CODE_QQ = 3;
	public static final int CODE_EMAIL = 4;
	public static final int CODE_ADDRESS = 5;
	public static final int CODE_IDEBTITY = 6;
	
	public static final int FROM_CONTACT = 1;
	public static final int FROM_PERSONAL = 2;
	
	//private BootstrapCircleThumbnail userPhoto;
	private InfoDispView userNumber;
	private InfoDispView userName;
	private InfoDispView userIdebtity;
	private InfoDispView userPhone;
	private InfoDispView userPhoneShort;
	private InfoDispView userQQ;
	private InfoDispView userEmail;
	private InfoDispView userAddress;
	private InfoDispView userCreateTime;
	private InfoDispView userLastTime;

	private Contact person;
	private int reqCode=0;
	private int fromWho=1;
	
	public static void actionStart(Context context, Contact contact,int from) {
		Intent intent = new Intent(context, UserActivity.class);
		intent.putExtra("person", contact);
		intent.putExtra("from", from);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_detail);

		person = (Contact) getIntent().getSerializableExtra("person");
		fromWho = getIntent().getIntExtra("from", FROM_CONTACT);
		// LogUtil.d("Tag", person.getId());

		((Button) findViewById(R.id.button_back)).setOnClickListener(this);
		((TextView) findViewById(R.id.textview_title)).setText("详细信息");
		findViewById(R.id.user_photo).setOnClickListener(this);
		
		//userPhoto = (BootstrapCircleThumbnail) findViewById(R.id.user_photo_r);
		userNumber = (InfoDispView) findViewById(R.id.user_number);
		userName = (InfoDispView) findViewById(R.id.user_name);
		userIdebtity = (InfoDispView) findViewById(R.id.user_idebtity);
		userPhone = (InfoDispView) findViewById(R.id.user_phone);
		userPhoneShort = (InfoDispView) findViewById(R.id.user_phoneshort);
		userQQ = (InfoDispView) findViewById(R.id.user_qq);
		userEmail = (InfoDispView) findViewById(R.id.user_email);
		userAddress = (InfoDispView) findViewById(R.id.user_address);
		userCreateTime = (InfoDispView) findViewById(R.id.user_create_time);
		userLastTime = (InfoDispView) findViewById(R.id.user_last_time);
		
		userNumber.setResources(null, "学号", person.getId()).setOnClickListener(this);
		userName.setResources(null, "姓名", person.getName()).setOnClickListener(this);
		userIdebtity.setResources(null, "身份", person.getIdebtity()).setOnClickListener(this);
		userPhone.setResources(null, "电话", person.getPhoneNumber()).setOnClickListener(this);
		userPhoneShort.setResources(null, "短号", person.getPhoneShort()).setOnClickListener(this);
		userQQ.setResources(null, "QQ", person.getQQNumber()).setOnClickListener(this);
		userEmail.setResources(null, "邮箱", person.getEmail()).setOnClickListener(this);
		userAddress.setResources(null, "地址", person.getAddress()).setOnClickListener(this);
		userCreateTime.setResources(null, "创建时间", person.getCreate_time()).setOnClickListener(this);
		userLastTime.setResources(null, "上次登录", person.getLast_time()).setOnClickListener(this);
		
		/*
		 * userNumber.setOnClickListener(this);
		 * userName.setOnClickListener(this);
		 * userIdebtity.setOnClickListener(this);
		 * userPhone.setOnClickListener(this);
		 * userPhoneShort.setOnClickListener(this);
		 * userQQ.setOnClickListener(this); userEmail.setOnClickListener(this);
		 * userAddress.setOnClickListener(this);
		 */
		
	}
    
	/* 
	{"type":"Info_Update_Only",
	"id":"1111",
	"pw":"1111",
	"update_name":"email",
	"value":"2333"
	}
	ID
	name
	creat_time
	idebtity
	email
	QQ
	phonenumber_long
	phonenumber_short
	address
	last_time
	*/
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button_back:
			finish();
			break;
		case R.id.user_photo:
			break;
		case R.id.user_number:
			//Modify("ID");
			break;
		case R.id.user_name:
			//Modify("name");
			break;
		case R.id.user_idebtity:
			reqCode = CODE_IDEBTITY;
			Modify("idebtity",person.getIdebtity());
			break;
		case R.id.user_phone:
			reqCode = CODE_PHONE;
			Modify("phonenumber_long",person.getPhoneNumber());
			break;
		case R.id.user_phoneshort:
			reqCode = CODE_PHONESHORT;
			Modify("phonenumber_short",person.getPhoneShort());
			break;
		case R.id.user_qq:
			reqCode = CODE_QQ;
			Modify("QQ",person.getQQNumber());
			break;
		case R.id.user_email:
			reqCode = CODE_EMAIL;
			Modify("email",person.getEmail());
			break;
		case R.id.user_address:
			reqCode = CODE_ADDRESS;
			Modify("address",person.getAddress());
			break;
		default:
			break;
		}
	}
	
	private void Modify(String key,String value){
		if (fromWho == FROM_PERSONAL) {
			startActivity(HttpUtil.ADDRESS_LOGIN_HANDLER, "Info_Update_Only", key,value);
		}
	}

	private void startActivity(String link,String type,String key,String value) {
		Intent intent = new Intent(UserActivity.this, EditActivity.class);
		intent.putExtra("link", link);
		intent.putExtra("type", type);
		intent.putExtra("key", key);
		intent.putExtra("value", value);
		startActivityForResult(intent, reqCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CODE_IDEBTITY:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userIdebtity.setContentText(returnData);
				
				MyApplication.ME.setIdebtity(returnData);
				MyApplication.saveString("user_idebtity", returnData);
			}
			break;
		case CODE_PHONE:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userPhone.setContentText(returnData);
				
				MyApplication.ME.setPhoneNumber(returnData);
				MyApplication.saveString("user_telephone", returnData);
			}
			break;
		case CODE_PHONESHORT:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userPhoneShort.setContentText(returnData);
				
				MyApplication.ME.setPhoneShort(returnData);
				MyApplication.saveString("user_phone_short", returnData);
			}
			break;
		case CODE_QQ:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userQQ.setContentText(returnData);
				
				MyApplication.ME.setQQNumber(returnData);
				MyApplication.saveString("user_qq_number", returnData);
			}
			break;
		case CODE_EMAIL:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userEmail.setContentText(returnData);
				
				MyApplication.ME.setEmail(returnData);
				MyApplication.saveString("user_email", returnData);
			}
			break;
		case CODE_ADDRESS:
			if (resultCode == RESULT_OK) {
				String returnData = data.getStringExtra("data_return");
				userAddress.setContentText(returnData);
				
				MyApplication.ME.setAddress(returnData);
				MyApplication.saveString("user_address", returnData);
			}
			break;
		default:
			break;
		}
	}
	
/*	void updateME(String key,String value){
		MyApplication.ME.setAddress(value);
		MyApplication.saveString(key, value);
	}*/
}
