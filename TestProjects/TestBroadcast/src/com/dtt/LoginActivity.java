package com.dtt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

	private EditText accountEdit;
	private EditText passwordEdit;
	private Button login;
	private CheckBox rememberPass;
	
	private SharedPreferences.Editor editor;
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		accountEdit = (EditText) findViewById(R.id.name);
		passwordEdit = (EditText) findViewById(R.id.pass);
		login = (Button) findViewById(R.id.buttonLogin);
		
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		rememberPass = (CheckBox) findViewById(R.id.remember_pass);
		boolean isRemember = pref.getBoolean("password_remembered", false);
		
		if (isRemember) {
			String account = pref.getString("name", "");
			String password = pref.getString("password", "");
			accountEdit.setText(account);
			passwordEdit.setText(password);
			rememberPass.setChecked(true);
		}
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String account = accountEdit.getText().toString();
				String password = passwordEdit.getText().toString();
				
				//假设账户为root 密码为root
				if (account.equals("root")&&password.equals("root")) {
					editor = pref.edit();
					if (rememberPass.isChecked()) {
						editor.putBoolean("password_remembered",true);
						editor.putString("name", account);
						editor.putString("password", password);
					}else {
						editor.clear();
					}
					editor.commit();
					
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				}else {
					Toast.makeText(LoginActivity.this, 
							"Account or password is invalid!", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
