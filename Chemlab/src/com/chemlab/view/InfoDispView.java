package com.chemlab.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.chemlab.R;

public class InfoDispView extends LinearLayout {

	private FontAwesomeText iconLeft;
	private FontAwesomeText iconRight;
	private TextView titleText;
	private TextView contentText;

	public InfoDispView(Context context) {
		this(context, null);
	}

	public InfoDispView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initUI(context);
	}

	public InfoDispView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initUI(context);
	}

	private void initUI(Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_person_item, this,
				true);
		iconLeft = (FontAwesomeText) findViewById(R.id.person_item_icon_left);
		iconRight = (FontAwesomeText) findViewById(R.id.person_item_icon_right);
		titleText = (TextView) findViewById(R.id.person_item_title);
		contentText = (TextView) findViewById(R.id.person_item_content);
	}

	public InfoDispView setResources(String icon_left, CharSequence title_text,
			CharSequence content_text) {
		if (icon_left != null && !("".equals(icon_left))) {
			iconLeft.setIcon(icon_left);
		}
		// iconRight.setIcon(icon_right);
		titleText.setText(title_text);
		contentText.setText(content_text);

		return this;
	}

	public void setIconLeft(String faIcon) {
		iconLeft.setIcon(faIcon);
	}

	public void setIconRight(String faIcon) {
		iconRight.setIcon(faIcon);
	}

	public void setTitleText(CharSequence text) {
		titleText.setText(text);
	}

	public void setContentText(CharSequence text) {
		contentText.setText(text);
	}

	public String getContentText() {
		return this.contentText.getText().toString().trim();
	}

	/*
	 * public void setListener(final CallbackListener l){
	 * this.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { if (l!=null){ l.click(); } } });
	 * }
	 * 
	 * public interface CallbackListener { void click(); }
	 */
}
