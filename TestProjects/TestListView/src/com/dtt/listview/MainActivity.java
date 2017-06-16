package com.dtt.listview;

import java.util.ArrayList;
import java.util.List;

import com.dtt.testlistview.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String [] data = {"Apple","Banana","Orange","Watermelon","Pear",
			"coco","kiwi","Strawberry","Cherry"};
	
	private List<Fruit> fruitList = new ArrayList<Fruit>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		/*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, android.R.layout.simple_list_item_1,data);*/
		initFruitList();
		FruitAdapter adapter = new FruitAdapter(MainActivity.this, 
				               R.layout.fruit_item, fruitList);
		ListView listView = (ListView)findViewById(R.id.listView);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Fruit fruit = fruitList.get(position);
				Toast.makeText(MainActivity.this, "You clicked "+fruit.getName(), 
						       Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	private void initFruitList() {
		/*Fruit apple = new Fruit("Apple", R.drawable.apple);
		fruitList.add(apple);*/
		Fruit fruit;
		int i=0;
		fruit=new Fruit(data[i++], R.drawable.apple);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.banana);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.orange);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.watermelon);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.pear);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.coco);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.kiwi);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.strawberry);
		fruitList.add(fruit);
		fruit=new Fruit(data[i++], R.drawable.cherry);
		fruitList.add(fruit);
		
		
		
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		//return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
			break;
		case R.id.about_item:
			Toast.makeText(this, "All rights (c) DTT", Toast.LENGTH_SHORT).show();
			break;
		case R.id.quit_item:
			finish();
			break;

		default:
			break;
		}
		return true;
	}

}
