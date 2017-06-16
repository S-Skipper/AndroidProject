package com.dtt.chemlab.activity;

import com.dtt.chemlab.R;
import com.dtt.chemlab.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	public static final int SUCCEESS = 1;
	public static final int FAIL = 2;
	
	private EditText nameText;
	private EditText passText;
	private CheckBox checkRemember;
	private Button loginButton;
	private Button registerButton;
	
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;
	
	private boolean isRemembered;
	private boolean isFirstLogined;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SUCCEESS:
				editor.putBoolean("IsFirstLoged", true);
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				String priority = (String) msg.obj;
				intent.putExtra("priority", priority);
				startActivity(intent);
				finish();
				break;
			case FAIL:
				Toast.makeText(LoginActivity.this, "Sorry!No such a user", 
						                     Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			editor.commit();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout0);
		
		nameText = (EditText) findViewById(R.id.loginName);
		passText = (EditText) findViewById(R.id.loginPasswd);
		checkRemember = (CheckBox) findViewById(R.id.check_box);
		loginButton = (Button) findViewById(R.id.btLogin);
		registerButton = (Button) findViewById(R.id.btReg);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		isFirstLogined = pref.getBoolean("IsFirstLoged", false);
		isRemembered = pref.getBoolean("pasword_remembered", false);

		if (isRemembered) {
			String name = pref.getString("name", "");
			String password = pref.getString("password", "");
			nameText.setText(name);
			passText.setText(password);
			checkRemember.setChecked(true);
		}
		
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		
		if (isFirstLogined&&isRemembered) {
			loginButton.callOnClick();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btLogin:
			final String name = nameText.getText().toString();
			final String pass = passText.getText().toString();
			
			editor = pref.edit();
			if (checkRemember.isChecked()) {
				editor.putBoolean("pasword_remembered", true);
				editor.putString("name", name);
				editor.putString("password", pass);
			} else {
				editor.clear();
			}
			//editor.commit();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					/*String result = HttpUtil.requestWebservice("HelloWorld",
					          null,null);
					Log.d("MainActivity", result);
					*/
					String result = HttpUtil.requestWebservice("TestLogin",
					          new String[]{"name","pass"},new String[]{name,pass});
					Message message = new Message();
					
					if (Integer.parseInt(result) <= 0 || Integer.parseInt(result)>3) {
						message.what=FAIL;
						handler.sendMessage(message);
					} else{
						message.what=SUCCEESS;
						message.obj = result;
						handler.sendMessage(message);
					}
				}
			}).start();
			break;
		case R.id.btReg:
			break;
		default:
			break;
		}
	}
}
