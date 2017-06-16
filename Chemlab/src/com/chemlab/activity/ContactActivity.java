package com.chemlab.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chemlab.R;
import com.chemlab.objs.Contact;

public class ContactActivity extends Activity implements OnClickListener{

	private Contact contact;

	public static void actionStart(Context context, Contact person) {
		Intent intent = new Intent(context, ContactActivity.class);
		intent.putExtra("contact", person);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_detail);

		contact = (Contact) getIntent().getSerializableExtra("contact");
		((TextView) findViewById(R.id.textview_title)).setText(contact.getName());
		findViewById(R.id.button_back).setOnClickListener(this);
		
		((TextView) findViewById(R.id.person_name)).setText(contact.getName());
		findViewById(R.id.call_contact).setOnClickListener(this);
		findViewById(R.id.send_message).setOnClickListener(this);
		findViewById(R.id.call_QQ).setOnClickListener(this);
		findViewById(R.id.view_detail).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.call_contact:
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
			startActivity(callIntent);
			break;
		case R.id.send_message:
			Intent msgIntent = new Intent(Intent.ACTION_SENDTO);
			msgIntent.setData(Uri.parse("smsto:" + contact.getPhoneNumber()));
			startActivity(msgIntent);
			break;
		case R.id.call_QQ:
			try {
				String url = "mqqwpa://im/chat?chat_type=wpa&uin="
		                +contact.getQQNumber()+"&version=1";
			    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
			} catch (Exception e) {
				//Toast.makeText(ContactActivity.this, "Wrong in QQ", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.view_detail:
			UserActivity.actionStart(ContactActivity.this, contact,UserActivity.FROM_CONTACT);
			break;
		case R.id.button_back:
			finish();
			break;
			
		}
	}

}
