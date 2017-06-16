package com.dtt;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText editText;
	private Button saveButton;
	private Button restoreButton;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText = (EditText) findViewById(R.id.edit);
		saveButton = (Button) findViewById(R.id.button);
		restoreButton = (Button) findViewById(R.id.button2);
		textView = (TextView) findViewById(R.id.text);

		saveButton.setOnClickListener(new OnClickListener() {

			@SuppressLint("CommitPrefEdits")
			@Override
			public void onClick(View arg0) {
				String inputString = editText.getText().toString();

				SharedPreferences.Editor editor = getSharedPreferences(
						"sp_data", MODE_PRIVATE).edit();
				editor.putString("save", inputString);

				editor.putString("name", "Tom");
				editor.putInt("age", 20);
				editor.putBoolean("married", false);

				editor.commit();

				Toast.makeText(MainActivity.this, "Save successful!",
						Toast.LENGTH_SHORT).show();
			}
		});

		restoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String loadString = "";
				SharedPreferences pref = getSharedPreferences("sp_data",
						MODE_PRIVATE);

				loadString = pref.getString("save", "Not found");
				textView.setText(loadString);

				String name = pref.getString("name", "");
				int age = pref.getInt("age", 0);
				boolean married = pref.getBoolean("married", false);

				Log.d("Tag", "name is " + name);
				Log.d("Tag", "age is " + age);
				Log.d("Tag", "married: " + married);
				
				Toast.makeText(MainActivity.this, "Load successful!",
						Toast.LENGTH_SHORT).show();
			}
		});

		/*
		 * String loadString = load(); if (!TextUtils.isEmpty(loadString)) {
		 * editText.setText(loadString);
		 * editText.setSelection(loadString.length());
		 * 
		 * Toast.makeText(this, "Load successful!", Toast.LENGTH_SHORT).show();
		 * }
		 */
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		/*
		 * String inputText = editText.getText().toString(); save(inputText);
		 */
	}

	/*
	 * public String load() { FileInputStream in = null; BufferedReader reader =
	 * null; StringBuilder content = new StringBuilder();
	 * 
	 * try { in = openFileInput("data"); reader = new BufferedReader(new
	 * InputStreamReader(in)); String line = "";
	 * 
	 * while ((line=reader.readLine())!=null) { content.append(line); } } catch
	 * (IOException e) { e.printStackTrace(); } finally{ if (reader != null) {
	 * try { reader.close(); } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * }
	 * 
	 * return content.toString(); }
	 * 
	 * public void save(String text) { FileOutputStream out = null;
	 * BufferedWriter writer = null;
	 * 
	 * try { out = openFileOutput("data", Context.MODE_PRIVATE); writer=new
	 * BufferedWriter(new OutputStreamWriter(out)); writer.write(text);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } finally{ try { if
	 * (writer!=null) { writer.close(); } } catch (IOException e) {
	 * e.printStackTrace(); } } }
	 */
}
