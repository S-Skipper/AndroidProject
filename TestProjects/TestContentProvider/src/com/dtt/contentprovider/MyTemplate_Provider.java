package com.dtt.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyTemplate_Provider extends ContentProvider {
	/*
	 * content: //com.example.app.provider/* 匹配任意表的uri 
	 * content: //com.example.app.provider/table/# 匹配任意行的uri
	 */
	
	public static final int TABLE1_DIR = 0;
	public static final int TABLE1_ITEM = 1;
	public static final int TABLE2_DIR = 2;
	public static final int TABLE2_ITEM = 3;
	
	private static UriMatcher uriMatcher;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.example.app.provider", "table1", TABLE1_DIR);
		uriMatcher.addURI("com.example.app.provider", "table1/#", TABLE1_ITEM);
		uriMatcher.addURI("com.example.app.provider", "table2", TABLE2_DIR);
		uriMatcher.addURI("com.example.app.provider", "table2/#", TABLE2_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, 
			            String[] selectionArgs,String orderBy) {
		switch (uriMatcher.match(uri)) {
		case TABLE1_DIR:   //query all data in table1
			
			break;
		case TABLE1_ITEM:  //query an item in table1

			break;
		case TABLE2_DIR:   //query all data in table2

			break;
		case TABLE2_ITEM:  //query an item in table2

			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case TABLE1_DIR:
			return "vnd.andriod.cursor.dir/vnd.com.example.app.provider.table1";
		case TABLE1_ITEM:
			return "vnd.andriod.cursor.item/vnd.com.example.app.provider.table1";
		case TABLE2_DIR:
			return "vnd.andriod.cursor.dir/vnd.com.example.app.provider.table2";
		case TABLE2_ITEM:
			return "vnd.andriod.cursor.item/vnd.com.example.app.provider.table2";
		default:
			break;
		}
		return null;
	}

}
