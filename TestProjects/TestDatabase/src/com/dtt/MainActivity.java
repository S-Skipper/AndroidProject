package com.dtt;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private MyDatabaseHelper dbHelper;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// create databases and tables
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
		Button createDatabase = (Button) findViewById(R.id.create_database);
		createDatabase.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbHelper.getWritableDatabase();
			}
		});

		// add data to tables
		Button addData = (Button) findViewById(R.id.add_data);
		addData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				// db.execSQL("insert into Book values(null,'AAA','DDD',100,100)");
				/*db.execSQL("insert into Book (name,author,pages,price) values(?,?,?,?)",
						new String[] { "The Da Vinci Code", "Dan Brown", "454","16.96" });*/

				ContentValues values = new ContentValues();
				values.put("name", "The Da Vinci Code"); 
				values.put("author","Dan Brown"); 
				values.put("pages", 454); 
				values.put("price",16.96); 
				db.insert("Book", null, values);
				 
				values.clear(); 
				values.put("name", "The Lost Symbol");
				values.put("author", "Dan Brown"); 
				values.put("pages", 510);
				values.put("price", 19.95); 
				db.insert("Book", null, values);
				 
				Toast.makeText(MainActivity.this, "Add successful",
						Toast.LENGTH_SHORT).show();
			}
		});

		// update data to tables
		Button updateData = (Button) findViewById(R.id.update_data);
		updateData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				// db.execSQL("update Book set price = 10 where id = 1");
				/*db.execSQL("update Book set price = ? where id = ?",
						new String[] { "10.99", "6" });*/

				ContentValues values = new ContentValues();
				values.put("price", 10.99); 
				db.update("Book", values,"name=?", new String[]{"The Da Vinci Code"});
				Toast.makeText(MainActivity.this, "Update successful",
						Toast.LENGTH_SHORT).show();
			}
		});

		// delete data from tables
		Button deleteData = (Button) findViewById(R.id.delete_data);
		deleteData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				// db.execSQL("delete from Book where id = 1");
				//db.execSQL("delete from Book where id=?", new String[] { "2" });

				db.delete("Book", "pages>?", new String[]{"100"});
				Toast.makeText(MainActivity.this, "Delete successful",
						Toast.LENGTH_SHORT).show();
			}
		});

		// query data from tables
		Button queryButton = (Button) findViewById(R.id.query_data);
		textView = (TextView) findViewById(R.id.text_result);

		queryButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				textView.setText("The query result:\n");
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				// Cursor cursor = db.rawQuery("select * from Book", null);
				Cursor cursor = db.query("Book", null, null, null, null, null,
						null);
				if (cursor.moveToFirst()) {
					do {
						String name = cursor.getString(cursor
								.getColumnIndex("name"));
						String author = cursor.getString(cursor
								.getColumnIndex("author"));
						int pages = cursor.getInt(cursor
								.getColumnIndex("pages"));
						double price = cursor.getDouble(cursor
								.getColumnIndex("price"));

						textView.append(name + "," + author + "," + pages + ","
								+ price + "\n");

						/*
						Log.d("Tag", "Book name is "+name); 
						Log.d("Tag","Book author is "+author); 
						Log.d("Tag","Book pages is "+pages); 
						Log.d("Tag","Book price is "+price);*/
					} while (cursor.moveToNext());
				}
				cursor.close();
				Toast.makeText(MainActivity.this, "Query done!",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		//Transaction(ÊÂÎñ)
		Button replaceData = (Button) findViewById(R.id.replace_data);
		replaceData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				db.beginTransaction();
				
				try {
					db.delete("Book", null, null);
					
					/*if (true) {
						throw new NullPointerException();
					}*/
					
					ContentValues values = new ContentValues();
					values.put("name", "Game of Thrones"); 
					values.put("author","George Martin"); 
					values.put("pages", 720); 
					values.put("price",20.85); 
					db.insert("Book", null, values);
					db.setTransactionSuccessful();
					Toast.makeText(MainActivity.this, "Replace successful",
							Toast.LENGTH_SHORT).show();
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					db.endTransaction();
				}
			}
		});
	}

}
