package com.dtt;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView msgListView;
	private EditText inputText;
	private Button sendButton;
	private MsgAdapter adapter;
	
	private List<Msg> msgList = new ArrayList<Msg>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initMsg();
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		inputText=(EditText) findViewById(R.id.input_text);
		sendButton = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		
		msgListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Msg msgDelMsg = (Msg) parent.getItemAtPosition(position);
				msgList.remove(position);
				adapter.notifyDataSetChanged(); //刷新adapter显示
				return false;
			}
		});
		
		sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String contentString = inputText.getText().toString();
				
				if (!"".equals(contentString)) {
					Msg msgSent = new Msg(contentString, Msg.TYPE_SENT);
					msgList.add(msgSent);
					adapter.notifyDataSetChanged(); //刷新adapter显示
					msgListView.setSelection(msgList.size()); //定位到最后一行
					inputText.setText("");
				}
			}
		});
		
		//Log.d("msg", "OnCreate!");
	}

	private void initMsg() {
		/*Msg msg1 = new Msg("Hello", Msg.TYPE_RECV);
		msgList.add(msg1);*/
		msgList.add(new Msg("Hello!", Msg.TYPE_RECV));
		msgList.add(new Msg("How are you?", Msg.TYPE_SENT));
		msgList.add(new Msg("I'm fine!", Msg.TYPE_RECV));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_settings:
			finish();
			break;
		default:
		}
		return true;
	}

}
