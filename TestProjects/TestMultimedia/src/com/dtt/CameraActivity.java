package com.dtt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity {

	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	public static final int CHOOSE_PHOTO =3;
	
	private Button takePhoto;
	private Button chooseFromAlbum;
	private ImageView picture;
	private Uri imageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosepic_layout);
		
		takePhoto = (Button) findViewById(R.id.take_photo);
		chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
		picture = (ImageView) findViewById(R.id.picture);
		
		takePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*File filePath1 = Environment.getExternalStorageDirectory();
				File f2 = Environment.getExternalStorageDirectory();
				File f3 = Environment.getDataDirectory();
				File f4 = Environment.getDownloadCacheDirectory();
				File f5 = Environment.getRootDirectory();
				
				String msg = filePath1.toString()+"\n"
				            + f2.toString()+"\n"
				            + f3.toString()+"\n"
				            + f4.toString()+"\n"
				            + f5.toString()+"\n";
				Toast.makeText(CameraActivity.this, msg, Toast.LENGTH_SHORT).show();
				*/
				
				//File filePath = Environment.getExternalStorageDirectory();
				//File filePath = new File("/mnt/sdcard2/image");
				File filePath = new File("/storage/sdcard1");
				//File outputImage = new File("/storage/sdcard1/output_image.jpg");
				File outputImage = new File(filePath,"output_image.jpg");
				
				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				imageUri = Uri.fromFile(outputImage);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);
			}
		});
		
		chooseFromAlbum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				startActivityForResult(intent, CHOOSE_PHOTO);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode==RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(
							getContentResolver().openInputStream(imageUri));
					picture.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PHOTO:
			if (resultCode == RESULT_OK) {
				//先判断手机系统版本，不同版本处理方法不同
				if (Build.VERSION.SDK_INT >=19) {
					handleImageOnKitKat(data);
				} else {
					handleImageBeforeKitKat(data);
				}
			}
			break;
		default:
			break;
		}
	}

	private void handleImageBeforeKitKat(Intent data) {
		Toast.makeText(this, "app 版本过低!", Toast.LENGTH_SHORT).show();
		/*String imagePath = null;
		Uri uri = data.getData();
		
		if (DocumentContract.isDocumentUri(this,uri)) {
			String docId = DocumentContract.getDocumentId(uri);
			
			//如果是document类型的uri,则通过document id处理
			if ("com.android.provides.media.documents".equals(
					                                 uri.getAuthority())) {
				String id = docId.split(":")[1]; //解析出数字格式的id
				String selection = MediaStore.Images.Media._ID+"="+id;
				imagePath = getImagePath(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			} else if ("com.android.provides.downloads.documents".equals(uri.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse(
				  "content://downloads/public_downloads"),Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			} else if ("content".equals(uri.getScheme())) {
				//如果不是document类型uri,则用普通方式处理
				imagePath= getImagePath(uri, null);
			}
				
		} 
		
		displayImage(imagePath);*/
	}

	private void handleImageOnKitKat(Intent data) {
		Uri uri = data.getData();
		String imagePath = getImagePath(uri,null);
		displayImage(imagePath);
	}

	private void displayImage(String imagePath) {
		if (imagePath!=null) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			picture.setImageBitmap(bitmap);
		} else {
			Toast.makeText(this, "Fail to get image!", Toast.LENGTH_SHORT).show();
		}
	}

	private String getImagePath(Uri uri, String selection) {
		String path=null;
		
		Cursor cursor = getContentResolver()
				.query(uri, null, selection, null, null);
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

}
