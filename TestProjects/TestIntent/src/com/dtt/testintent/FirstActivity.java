package com.dtt.testintent;

//import com.dtt.testintent.Serializable.Person;
import com.dtt.testintent.Parcelable.Person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class FirstActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);

		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*
				 * Toast.makeText(FirstActivity.this, "Button1!",
				 * Toast.LENGTH_SHORT).show();
				 */
				/*
				 * Intent intent = new
				 * Intent(FirstActivity.this,SecondActivity.class);
				 * startActivity(intent);
				 */
				/*
				 * Intent intent = new
				 * Intent("com.dtt.testintent.SecondAction");
				 * intent.addCategory("com.dtt.testintent.MyCategory");
				 * startActivity(intent);
				 */
				/*
				 * String data = "Hello SecondActivity!"; Intent intent = new
				 * Intent(FirstActivity.this, SecondActivity.class);
				 * intent.putExtra("msg", data); //startActivity(intent);
				 * startActivityForResult(intent,0);
				 */

				// Pass object with intent
				Person person = new Person();
				person.setName("Tom");
				person.setAge(20);

				Intent intent = new Intent(FirstActivity.this,
						SecondActivity.class);
				intent.putExtra("person_data", person);
				startActivity(intent);
			}
		});
	}

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { // super.onActivityResult(requestCode,
	 * resultCode, data); switch (requestCode) { case 0: if (resultCode ==
	 * RESULT_OK) { String msg = data.getStringExtra("data_return");
	 * Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); } break; default: }
	 * }
	 */
}
