package com.dtt.chemlab.activity;

import com.dtt.chemlab.R;
import com.dtt.chemlab.util.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends Activity {

	private String id;
	private EditText nameText;
	private EditText phoneText;
	private EditText qqText;
	private EditText emailText;
	private Button submitButton;
	
	public static void actionStart(Context context, String id) {
		Intent intent = new Intent(context, UpdateContactActivity.class);
		intent.putExtra("id", id);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_contacts);
		
		nameText = (EditText) findViewById(R.id.id_name);
		phoneText = (EditText) findViewById(R.id.id_phone);
		qqText = (EditText) findViewById(R.id.id_qq);
		emailText = (EditText) findViewById(R.id.id_email);
		submitButton =  (Button) findViewById(R.id.id_submit);
		
		id = getIntent().getStringExtra("id");
		((EditText) findViewById(R.id.id_update)).setText(id);
		
		submitButton.setOnClickListener(new OnClickListener() {
			String name,phone,qq,email,sqlString,response;
			@Override
			public void onClick(View v) {
				name = nameText.getText().toString().trim();
				phone = phoneText.getText().toString().trim();
				qq = qqText.getText().toString().trim();
				email = emailText.getText().toString().trim();
				//id = ((EditText) findViewById(R.id.id_update)).getText().toString();
				sqlString = "update contact_info set"
						    +" name='"+name
						    +"',phoneNumber='"+phone
						    +"',qqNumber='"+qq
						    +"',email='"+email
				            +"' where id = "+id;
				
				AsyncTask.execute(new Runnable(){

					@Override
					public void run() {
						response=HttpUtil.requestWebservice(null,"EditContacts", new String[]{"cmdStr"}, new String[]{sqlString});
					    if (!"OK".equals(response)) {
							Toast.makeText(getApplication(), "Failed!", Toast.LENGTH_SHORT).show();
						}else {
							Toast.makeText(getApplication(), "Successed!", Toast.LENGTH_SHORT).show();
						}
					}
					
				});
				
				
			}
		});
	}

}
