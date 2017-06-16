package com.example.demotest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zjb.view.MyLinerLay;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * @author Yongke.Pan
 * @E-mail: itmadder@163.com
 * @Version 创建时间：2015-5-28 下午2:43:19
 * @Des:
 */
// 为expandable list view 提供内容的基类
public class InfoDetailsAdapter extends BaseExpandableListAdapter {
	private Activity activity;
	private List<String> group;
	private List<List<String>> child;
	private OnExpandListener listener;
	private Map<Integer, Boolean> maps = new HashMap<Integer, Boolean>();
	private float start;

	public InfoDetailsAdapter(Activity a, List<String> group,
			List<List<String>> child) {
		activity = a;
		this.group = group;
		this.child = child;
		for (int i = 0; i < group.size(); i++)
			maps.put(i, false);
	}

	public boolean isExpand(int position) {
		boolean isExpand = maps.get(position);
		maps.put(position, !isExpand);
		return isExpand;
	}

	// child method stub
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		// System.out.println("*******************"+child.get(groupPosition).get(childPosition));
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	// group method stub
	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String string = child.get(groupPosition).get(childPosition);
		return getGenericView(string);// getGenericView(groupPosition, 1,
										// childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return child.get(groupPosition).size();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		String string = group.get(groupPosition);
		return getGenericView(groupPosition, 0, 0);
	}

	public TextView getGenericView(String s) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(activity);
		text.setLayoutParams(lp);
		// Center the text vertically
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		text.setPadding(36, 0, 0, 0);
		text.setText(s);
		return text;
	}

	// View stub to create Group/Children 's View
	public View getGenericView(int groupPosition, int type, int childPosition) {
		// Layout parameters for the ExpandableListView
		Log.i("TAG", "getGenericView");
		MyLinerLay lin = (MyLinerLay) activity.getLayoutInflater().inflate(
				R.layout.lin, null);
		switch (type) {
		case 0:
			lin.setOnTouchListener(new MyTouchListener(groupPosition));
			break;

		case 1:
			break;
		}

		return lin;
	}

	class MyTouchListener implements OnTouchListener {
		private int posi;

		public MyTouchListener(int groupPosition) {
			this.posi = groupPosition;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			((MyLinerLay) v).move(event);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// Log.i("TAG", "DOWN");
				start = event.getRawX();
				break;
			case MotionEvent.ACTION_UP:

				if (start == event.getRawX()) {
					// Log.i("TAG", "UP");
					listener.onExpand(posi);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				// Log.i("TAG", "MOVE");

				break;
			}

			return true;
		}

	}

	public void setOnExpandListener(OnExpandListener listener) {
		this.listener = listener;
	}

	public interface OnExpandListener {
		void onExpand(int groupPosition);
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
}