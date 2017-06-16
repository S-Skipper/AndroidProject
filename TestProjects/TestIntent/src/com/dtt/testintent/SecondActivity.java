package com.dtt.testintent;

//import com.dtt.testintent.Serializable.Person;
import com.dtt.testintent.Parcelable.Person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class SecondActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);

		/*Intent intent = getIntent();
		String data = intent.getStringExtra("msg");
		Toast.makeText(this, data, Toast.LENGTH_SHORT).show();*/
		
		Intent intent = getIntent();
		//Person person = (Person) intent.getSerializableExtra("person_data");
		Person person = (Person) intent.getParcelableExtra("person_data");
		
		String msg = "Info: "+person.getName()+", "+person.getAge();
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Toast.makeText(SecondActivity.this, "Button2!",
				 * Toast.LENGTH_SHORT).show();
				 */
				/*
				 * Intent intent = new
				 * Intent(SecondActivity.this,FirstActivity.class);
				 * startActivity(intent);
				 */
				/*
				 * Intent intent = new Intent(Intent.ACTION_VIEW);
				 * intent.setData(Uri.parse("http://www.baidu.com"));
				 */
				/*
				 * Intent intent = new Intent(Intent.ACTION_DIAL);
				 * intent.setData(Uri.parse("tel:10086"));
				 * startActivity(intent);
				 */
				/*Intent intent = new Intent();
				intent.putExtra("data_return", "Hello FirstActivity!");
				setResult(RESULT_OK, intent);
				finish();*/
			}
		});

	}

}
