package com.npp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * 
 * @author Himi
 *
 */
public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//原图
	private Bitmap bmp_old;
	//通过9patch工具生成的“.9.png”图
	private Bitmap bmp_9path;
	//声明NinePatch
	private NinePatch np;

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
		//将图片资源生成位图
		bmp_old = BitmapFactory.decodeResource(getResources(), R.drawable.iamge);
		bmp_9path = BitmapFactory.decodeResource(getResources(), R.drawable.nine_image);
		//实例NinePatch实例
		np = new NinePatch(bmp_9path, bmp_9path.getNinePatchChunk(), null);
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
			//---------这里是为了更好的展示出缩放拉伸后的区别
			RectF rectf_old_two = new RectF(0, 50, bmp_old.getWidth() * 2, 120 + bmp_old.getHeight() * 2); 
			RectF rectf_old_third = new RectF(0, 120 + bmp_old.getHeight() * 2, bmp_old.getWidth() * 3, 140 + bmp_old.getHeight() * 2 + bmp_old.getHeight()
					* 3);
			// --------下面是对正常png绘画方法-----------  
			canvas.drawBitmap(bmp_old, 0, 0, paint);
			canvas.drawBitmap(bmp_old, null, rectf_old_two, paint);
			canvas.drawBitmap(bmp_old, null, rectf_old_third, paint);
			RectF rectf_9path_two = new RectF(250, 50, 250 + bmp_9path.getWidth() * 2, 90 + bmp_9path.getHeight() * 2);
			RectF rectf_9path_third = new RectF(250, 120 + bmp_9path.getHeight() * 2, 250 + bmp_9path.getWidth() * 3, 140 + bmp_9path.getHeight() * 2
					+ bmp_9path.getHeight() * 3);
			canvas.drawBitmap(bmp_9path, 250, 0, paint);
			// --------下面是".9.png"图像的绘画方法-----------  
			np.draw(canvas, rectf_9path_two);
			np.draw(canvas, rectf_9path_third);
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
}
