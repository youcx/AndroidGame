package com.gp;

import java.util.Vector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnTouchListener;

/**
 * 
 * @author Himi
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable, OnGestureListener, OnTouchListener {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//检测手势的类
	private GestureDetector gesture;
	//保存所有添加的icon位图
	private Vector<Bitmap> vec = new Vector<Bitmap>();

	/**
	 * SurfaceView初始化函数
	 */
public MySurfaceView(Context context) {
	super(context);
	sfh = this.getHolder();
	sfh.addCallback(this);
	paint = new Paint();
	paint.setColor(Color.WHITE);
	paint.setAntiAlias(true);
	setFocusable(true);
	//实例GestureDetector
	gesture = new GestureDetector(this);
	//为当前视图设置触屏监听器
	this.setOnTouchListener(this);
}

	/**
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
	}

	/**
	 * 游戏绘图
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.BLACK);
				for (int i = 0; i < vec.size(); i++) {
					canvas.drawBitmap(vec.elementAt(i), i * 5, 50, paint);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * 按键事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 游戏逻辑
	 */
	private void logic() {
	}

	@Override
	public void run() {
		while (flag) {
			long start = System.currentTimeMillis();
			myDraw();
			logic();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 50) {
					Thread.sleep(50 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gesture.onTouchEvent(event);
	}

	//按下
	@Override
	public boolean onDown(MotionEvent e) {
		Log.e("", "onDown");
		return false;
	}

	// 短暂按下抬起
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.e("", "onSingleTapUp");
		return false;
	}

	// 先短暂按下 、然后滑动、最后 抬起
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.e("", "onFling");
		//往图片容器里添加一个新的icon位图
		vec.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon));
		return false;
	}

	// 先短暂按下 、然后滑动
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.e("", "onScroll" + distanceX + "---" + distanceY);
		return false;
	}

	// 先短暂按下 、短按不滑动
	@Override
	public void onShowPress(MotionEvent e) {
		Log.e("", "onShowPress");
	}

	// 长按不滑动
	@Override
	public void onLongPress(MotionEvent e) {
		Log.e("", "onLongPress");
	}
}
