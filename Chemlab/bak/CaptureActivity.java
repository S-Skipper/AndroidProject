package com.chemlab.activity;

import com.chemlab.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptureActivity extends Activity {

	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * ��ʾɨ����
	 */
	private TextView mTextView;
	/**
	 * ��ʾɨ���ĵ�ͼƬ
	 */
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testcapture);

		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);

		// �����ť��ת����ά��ɨ����棬�����õ���startActivityForResult��ת
		// ɨ������֮������ý���
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CaptureActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				// ��ʾɨ�赽������
				mTextView.setText(bundle.getString("result"));
				// ��ʾ
				mImageView.setImageBitmap((Bitmap) data
						.getParcelableExtra("bitmap"));
			}
			break;
		}
	}
}
