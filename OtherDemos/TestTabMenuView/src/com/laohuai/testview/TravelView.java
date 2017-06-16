package com.laohuai.testview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

public class TravelView extends SurfaceView implements SurfaceHolder.Callback {

	private OnTravelListener travelListener;
	// 图片资源
	private Bitmap backgroundImg;
	private Bitmap[] itemImgs;
	private String[] itemNames;
	private Bitmap coverImg;
	private Bitmap leftArrow;
	private Bitmap rightArrow;

	// 移动消耗的时间ms
	private int costTime = 300;

	// 外围框架的宽度，亦即coverImg的宽度，因为coverImg要包裹住itemImg和itemName，也就是严格按照美工出的素材图的大小来绘制
	private float frameWidth;
	// 外围框架的高度，亦即backgroundImg的高度
	private float frameHeight;
	// 每一个元素的宽度
	private float itemWidth;
	// 每一个元素的高度
	private float itemHeight;
	// 屏幕宽度
	private float screenWidth;

	// 用于保存该SurfaceView的SurfaceHolder，使用其为SurfaceView添加回调函数
	private SurfaceHolder holder;

	// 指示该SurfaceView是否已经初始化完成，用于指示是否可以进行绘制操作
	private boolean isInited = false;
	// 指示该SurfaceView是否已经执行了oonSurfaceCreate的回调函数，用于指示是否可以进行绘制操作
	private boolean isCreated = false;

	// SurfaceView左边界距离屏幕左边界的距离,默认一开始是0
	private float offsetX;
	// 当前所指向的item的索引.默认0
	private int currentIndex;
	// 当前的cover左边界的x坐标
	private float coverX;

	// item是否可以选择的
	private boolean[] isSelectable;

	private Paint paint;
	private TextPaint textPaint;

	private GestureDetector gd;

	private float destionX;
	private float step;
	private Runnable coverRun;
	private boolean isPaused = false;
	private boolean isCoverRunning = false;
	private boolean isFollowWith;

	private float maxOffsetX;

	public TravelView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setTextAlign(Align.LEFT);
		textPaint.setTextSize(15);
		textPaint.setColor(0xff000000);

		OnGestureListener gestureListener = new OnGestureListener() {
			public boolean onSingleTapUp(MotionEvent e) {
				doOnSingleTapStuff(e.getX());
				return true;
			}

			public void onShowPress(MotionEvent e) {

			}

			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				if (offsetX + distanceX > maxOffsetX) {
					offsetX = maxOffsetX;
				} else if (offsetX + distanceX < 0) {
					offsetX = 0;
				} else {
					offsetX += distanceX;
				}
				doDrawStuff();
				return true;
			}

			public void onLongPress(MotionEvent e) {

			}

			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				return false;
			}

			public boolean onDown(MotionEvent e) {
				return true;
			}
		};
		gd = new GestureDetector(gestureListener);

		coverRun = new Runnable() {
			public void run() {
				while (destionX != coverX) {
					long time1 = System.currentTimeMillis();
					// 暂停以后就一直阻塞在
					if (isPaused || !isCreated || !isInited) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					coverX += step;
					// 向右滑动
					if (step > 0 && coverX > destionX) {
						coverX = destionX;
					}
					// 向左滑动
					if (step < 0 && coverX < destionX) {
						coverX = destionX;
					}

					if (isFollowWith) {
						doComputeOffsetX();
					}

					doDrawStuff();
					long time2 = System.currentTimeMillis();
					try {
						Thread.sleep(Math.max(0, 1000 / 24 - (time2 - time1)));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				isCoverRunning = false;
				if (travelListener != null) {
					travelListener.onTravel(currentIndex);
				}

			}
		};

		holder = getHolder();
		holder.addCallback(this);
	}

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		doChange();
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		doCreate();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
		doDestroy();
	}

	// 在SurfaceChange时执行
	private void doChange() {
		isPaused = false;

		doDrawStuff();
	}

	// 在surfaceCreate时执行
	private void doCreate() {
		if (!isCreated) {
			isCreated = true;
			screenWidth = getWidth();
			if (isInited) {
				maxOffsetX = itemImgs.length * frameWidth - screenWidth;
				doDrawStuff();
			}
		}
	}

	// 在surfaceDestroy执行
	private void doDestroy() {
		isPaused = true;
	}

	public void initView(int backgroundID, int coverID, int[] itemIDs,
			String[] itemNames, boolean[] isSelectable) {
		Resources resources = getResources();

		backgroundImg = BitmapFactory.decodeResource(resources, backgroundID);
		coverImg = BitmapFactory.decodeResource(resources, coverID);

		leftArrow = BitmapFactory.decodeResource(resources,
				R.drawable.arrow_left);

		rightArrow = BitmapFactory.decodeResource(resources,
				R.drawable.arrow_right);

		itemImgs = new Bitmap[itemIDs.length];
		for (int i = 0; i < itemImgs.length; i++) {
			itemImgs[i] = BitmapFactory.decodeResource(resources, itemIDs[i]);
		}
		this.itemNames = itemNames;
		this.isSelectable = isSelectable;

		frameWidth = coverImg.getWidth();
		frameHeight = backgroundImg.getHeight();
		itemWidth = itemImgs[0].getWidth();
		itemHeight = itemImgs[0].getHeight();

		coverX = 0;
		offsetX = 0;

		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.height = (int) frameHeight;
		setLayoutParams(layoutParams);

		isInited = true;

		if (isCreated) {
			maxOffsetX = itemIDs.length * frameWidth - screenWidth;
			doDrawStuff();
		}
	}

	// 处理绘制操作
	private void doDrawStuff() {
		synchronized (holder) {
			Canvas canvas = holder.lockCanvas();
			doDrawBackground(canvas);
			doDrawCover(canvas);
			doDrawItems(canvas);
			doDrawArrows(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	// 绘制箭头
	private void doDrawArrows(Canvas canvas) {

		// 超过一个元素宽度，绘制像左的箭头
		if (offsetX > frameWidth) {

			canvas.drawBitmap(
					leftArrow,
					new Rect(0, 0, leftArrow.getWidth(), leftArrow.getHeight()),
					new RectF(0, (frameHeight - leftArrow.getHeight()) / 2,
							leftArrow.getWidth(), (frameHeight - leftArrow
									.getHeight()) / 2 + leftArrow.getHeight()),
					paint);

		}

		// 当最后一个元素的左边界距离屏幕右边界的距离大于0时候，绘制向右箭头
		if (maxOffsetX - offsetX >= frameWidth) {

			canvas.drawBitmap(
					rightArrow,
					new Rect(0, 0, rightArrow.getWidth(), rightArrow
							.getHeight()),
					new RectF(screenWidth - rightArrow.getWidth(),
							(frameHeight - rightArrow.getHeight()) / 2,
							screenWidth - rightArrow.getWidth()
									+ rightArrow.getWidth(),
							(frameHeight - rightArrow.getHeight()) / 2
									+ rightArrow.getHeight()), paint);
		}

	}

	// 绘制背景
	private void doDrawBackground(Canvas canvas) {
		canvas.drawBitmap(
				backgroundImg,
				new Rect(0, 0, backgroundImg.getWidth(), backgroundImg
						.getHeight()),
				new RectF(0, 0, screenWidth, frameHeight), paint);
	}

	// 绘制cover
	private void doDrawCover(Canvas canvas) {
		canvas.drawBitmap(coverImg, new Rect(0, 0, coverImg.getWidth(),
				coverImg.getHeight()), new RectF(coverX - offsetX, 0, coverX
				- offsetX + frameWidth, frameHeight), paint);
	}

	// 绘制items
	private void doDrawItems(Canvas canvas) {

		float textHeight = textPaint.descent();

		for (int i = 0; i < itemImgs.length; i++) {
			canvas.drawBitmap(
					itemImgs[i],
					new Rect(0, 0, itemImgs[i].getWidth(), itemImgs[i]
							.getHeight()), new RectF(i * frameWidth
							+ (frameWidth - itemWidth) / 2 - offsetX,
							(frameHeight - textHeight - itemHeight) / 2, i
									* frameWidth + (frameWidth - itemWidth) / 2
									- offsetX + itemWidth, (frameHeight
									- textHeight - itemHeight)
									/ 2 + itemHeight), paint);
		}

		for (int i = 0; i < itemNames.length; i++) {
			float textWidth = textPaint.measureText(itemNames[i]);
			canvas.drawText(itemNames[i], i * frameWidth
					+ (frameWidth - textWidth) / 2 - offsetX, (frameHeight
					- textHeight - itemHeight)
					/ 2 + itemHeight + 10, textPaint);
		}

	}

	public boolean onTouchEvent(MotionEvent event) {
		return gd.onTouchEvent(event);
	}

	// 单击操作的逻辑处理
	private void doOnSingleTapStuff(float x) {
		int clickIndex = (int) ((x + offsetX) / frameWidth);

		// 点击的索引和当前索引位置不一样且该索引是可以点击的
		if (currentIndex != clickIndex && isSelectable[clickIndex]) {
			// TODO 让cover移动到指定的索引位置处，然后看是否有设置监听事件，如果有的话，执行该监听事件的逻辑
			this.currentIndex = clickIndex;
			if (isCoverRunning) {
				isFollowWith = false;
				doComputeMoveValues(clickIndex);
			} else {
				isFollowWith = false;
				doComputeMoveValues(clickIndex);
				startCoverRun();
			}
		}

	}

	// 计算目的地以及步长
	private void doComputeMoveValues(int index) {
		destionX = index * frameWidth;
		step = (destionX - coverX) / costTime * (1000 / 24);
	}

	// 跳转到该index位置处
	private void startCoverRun() {
		isCoverRunning = true;
		new Thread(coverRun).start();
	}

	interface OnTravelListener {
		public void onTravel(int index);
	}

	public void setOnTravelListener(OnTravelListener travelListener) {
		this.travelListener = travelListener;
	}

	// 跳转到指定的index处,并且实时更新offsetX的值,亦即镜头跟随cover移动的效果
	public void moveToAndFollowWith(int index) {
		if (index != currentIndex && isSelectable[index]) {
			this.currentIndex = index;
			isFollowWith = true;
			doComputeMoveValues(index);
			doComputeOffsetX();

			// Tread正在运行
			if (!isCoverRunning) {
				isCoverRunning = true;
				new Thread(coverRun).start();
			}
		}
	}

	// 跳转到指定的index处，镜头不跟随移动
	public void moveTo(int index) {

		if (index != currentIndex && isSelectable[index]) {
			this.currentIndex = index;
			isFollowWith = false;
			doComputeMoveValues(index);
			// Tread正在运行
			if (!isCoverRunning) {
				isCoverRunning = true;
				new Thread(coverRun).start();
			}
		}
	}

	// 当镜头跟随时，计算偏移值
	private void doComputeOffsetX() {
		float temp = coverX - screenWidth / 2;

		if (temp <= 0) {
			offsetX = 0;
		} else if (temp >= maxOffsetX) {
			offsetX = maxOffsetX;
		} else {
			offsetX = temp;
		}

	}

}
