package com.laohuai.testview.views;

import android.view.LayoutInflater;
import android.view.View;

import com.laohuai.testview.R;

/**
 * 一个用于测试的view保持者，该类用于返回一个view，因为这个view除了展示以外没有任何其他的逻辑处理，所以只是传递了一个LayoutInflater
 * ，而在实际使用过程中，根据您的具体情况，可以传递需要的参数
 * 
 * @author laohuai
 * 
 */

public class ViewHolder0 {
	private LayoutInflater inflater;

	private View view;

	public ViewHolder0(LayoutInflater inflater) {
		this.inflater = inflater;
	}

	public View getView() {
		view = inflater.inflate(R.layout.view0, null);

		return view;

	}

}
