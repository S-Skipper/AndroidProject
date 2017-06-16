package com.chemlab.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chemlab.R;


/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MyGridAdapter extends BaseAdapter {
	private Context mContext;

	private String[] mMenuTexts;
	private int[] mMenuImgs;

	public MyGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	public MyGridAdapter(Context mContext, int[] resIds, String[] texts){
		super();
		
		this.mContext = mContext;
		setMenuItemIconsAndTexts(resIds,texts);
	}
	
	@Override
	public int getCount() {
		return mMenuTexts.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_grid_menu, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(mMenuImgs[position]);

		tv.setText(mMenuTexts[position]);
		return convertView;
	}
	
	/**
	 * 设置菜单条目的图标和文本
	 * 
	 * @param resIds
	 */
	public void setMenuItemIconsAndTexts(int[] resIds, String[] texts)
	{
		
		mMenuImgs = resIds;
		mMenuTexts = texts;

		/*// 参数检查
		if (resIds == null && texts == null)
		{
			throw new IllegalArgumentException("菜单项文本和图片至少设置其一");
		}

		// 初始化mMenuCount
		mMenuItemCount = resIds == null ? texts.length : resIds.length;

		if (resIds != null && texts != null)
		{
			mMenuItemCount = Math.min(resIds.length, texts.length);
		}

		addMenuItems();
        */
	}
}

/**
 * @Description:万能的viewHolder
 * @author http://blog.csdn.net/finddreams
 */ 
class BaseViewHolder {
	    @SuppressWarnings("unchecked")
	    public static <T extends View> T get(View view, int id) {
	        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
	        if (viewHolder == null) {
	            viewHolder = new SparseArray<View>();
	            view.setTag(viewHolder);
	        }
	        View childView = viewHolder.get(id);
	        if (childView == null) {
	            childView = view.findViewById(id);
	            viewHolder.put(id, childView);
	        }
	        return (T) childView;
	    }

}