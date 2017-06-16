package com.dtt.chemlab.activity;

import java.util.ArrayList;
import java.util.List;

import com.dtt.chemlab.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity {

	private Button searchButton;
	private EditText editText;
	private ListView resultLv;

	private ArrayAdapter<String> adapter;
	private List<String> result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);

		result = new ArrayList<String>();

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, result);

		editText = (EditText) findViewById(R.id.edit_search);
		searchButton = (Button) findViewById(R.id.bt_search);
		resultLv = (ListView) findViewById(R.id.search_result_list);
		
		resultLv.setAdapter(adapter);
		
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String nameSearch = editText.getText().toString().trim();
				search(nameSearch);
			}
		});

		resultLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				Toast.makeText(SearchActivity.this, result.get(position).toString(), 
						Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void search(String name){
		result.add(name);
		adapter.notifyDataSetChanged();
		//Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
	}

}
