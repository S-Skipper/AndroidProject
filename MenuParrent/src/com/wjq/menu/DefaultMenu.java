package com.wjq.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DefaultMenu extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * 
		 * add()方法的四个参数，依次是：
		 * 
		 * 1、组别，如果不分组的话就写Menu.NONE,
		 * 
		 * 2、Id，这个很重要，Android根据这个Id来确定不同的菜单
		 * 
		 * 3、顺序，那个菜单现在在前面由这个参数的大小决定
		 * 
		 * 4、文本，菜单的显示文本
		 */

		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "删除").setIcon(

		android.R.drawable.ic_menu_delete);

		// setIcon()方法为菜单设置图标，这里使用的是系统自带的图标，同学们留意一下,以

		// android.R开头的资源是系统提供的，我们自己提供的资源是以R开头的

		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "保存").setIcon(

		android.R.drawable.ic_menu_edit);

		menu.add(Menu.NONE, Menu.FIRST + 3, 6, "帮助").setIcon(

		android.R.drawable.ic_menu_help);

		menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(

		android.R.drawable.ic_menu_add);

		menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(

		android.R.drawable.ic_menu_info_details);

		menu.add(Menu.NONE, Menu.FIRST + 6, 3, "发送").setIcon(

		android.R.drawable.ic_menu_send);

		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case Menu.FIRST + 1:

			Toast.makeText(this, "删除菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		case Menu.FIRST + 2:

			Toast.makeText(this, "保存菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		case Menu.FIRST + 3:

			Toast.makeText(this, "帮助菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		case Menu.FIRST + 4:

			Toast.makeText(this, "添加菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		case Menu.FIRST + 5:

			Toast.makeText(this, "详细菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		case Menu.FIRST + 6:

			Toast.makeText(this, "发送菜单被点击了", Toast.LENGTH_LONG).show();

			break;

		}

		return false;

	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		Toast.makeText(this, "选项菜单关闭了", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		Toast.makeText(this,
				"选项菜单显示之前onPrepareOptionsMenu方法会被调用，你可以用此方法来根据打当时的情况调整菜单",
				Toast.LENGTH_LONG).show();

		// 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用

		return true;

	}
}
