package com.dtt.chemlab.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.dtt.chemlab.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;  
import com.nostra13.universalimageloader.core.DisplayImageOptions;  
import com.nostra13.universalimageloader.core.ImageLoader;  
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;  
import com.nostra13.universalimageloader.core.assist.ImageScaleType;  
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;  
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class BannerView extends FrameLayout {
    private final static boolean isAutoPlay = true;
    private List<String> imageUris;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private int currentItem  = 0;
    private ScheduledExecutorService scheduledExecutorService;
    
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // 
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem);
        }
    };
    //使用ImageLoader加载网络图片
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options = new DisplayImageOptions.Builder()
        /*.showImageForEmptyUri(R.drawable.cardbg)
	    .showImageOnFail(R.drawable.cardbg)*/
	    .resetViewBeforeLoading(true)
	    .cacheOnDisk(true)
	    .imageScaleType(ImageScaleType.EXACTLY)
	    .bitmapConfig(Bitmap.Config.RGB_565)
	    .considerExifParams(true)
	    .displayer(new FadeInBitmapDisplayer(300)).build();
    
    public BannerView(Context context) {
        this(context,null);
    }
    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 
        initUI(context);
        if(!(imageUris.size()<=0))
        {
            setImageUris(imageUris);
        }
        if(isAutoPlay){
            startPlay();
        }

    }
    private void initUI(Context context){
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<ImageView>();
        imageUris=new ArrayList<String>();
        LayoutInflater.from(context).inflate(R.layout.view_banner_image, this, true);
        mLinearLayout=(LinearLayout)findViewById(R.id.dotsImg);
        mViewPager = (ViewPager) findViewById(R.id.imagePager);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        imageLoader.init(config.build());
    }
    public void setImageUris(List<String> imageuris)
    {
        for(int i=0;i<imageuris.size();i++)
        {
            imageUris.add(imageuris.get(i));
        }
        for (int i = 0; i < imageUris.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//铺满屏幕
            imageLoader.displayImage(imageUris.get(i), imageView, options);
            imageViewsList.add(imageView);
            ImageView viewDot =  new ImageView(getContext());
            /*if(i == 0){
                viewDot.setBackgroundResource(R.drawable.dot_focused);
            }else{
                viewDot.setBackgroundResource(R.drawable.dot);
            }*/
            dotViewsList.add(viewDot);
            mLinearLayout.addView(viewDot);
        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
    }


    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new BannerTask(), 1, 4, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unused")
    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 设置选中的圆点的背景
     * @param selectItems
     */
    private void setImageBackground(int selectItems){
        /*for(int i=0; i<dotViewsList.size(); i++){
            if(i == selectItems){
                dotViewsList.get(i).setBackgroundResource(R.drawable.dot_focused);
            }else{
                dotViewsList.get(i).setBackgroundResource(R.drawable.dot);
            }
        }*/
    }
    private class MyPagerAdapter  extends PagerAdapter {
        public void destroyItem(View container, int position, Object object) {
            
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        public Object instantiateItem(View container, int position) {
            // 
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        public int getCount() {
            // 
            return imageViewsList.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            // 
            return arg0 == arg1;
        }
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // 
        }

        public Parcelable saveState() {
            // 
            return null;
        }
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        boolean isAutoPlay = false;
        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isAutoPlay = false;
                    break;
                case 2:
                    isAutoPlay = true;
                    break;
                case 0:                //如果滑到最后，就从头开始
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mViewPager.setCurrentItem(0);
                    }
                    //如果滑到头就从尾开始
                    else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // 
        }

        @Override
        public void onPageSelected(int pos) {
            // 
            setImageBackground(pos);
        }
    }



    private class BannerTask implements Runnable{
        @Override
        public void run() {
            // 
            synchronized (mViewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }
    
}
