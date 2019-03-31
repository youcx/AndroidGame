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
	//������Ƶ���
	private GestureDetector gesture;
	//����������ӵ�iconλͼ
	private Vector<Bitmap> vec = new Vector<Bitmap>();

	/**
	 * SurfaceView��ʼ������
	 */
public MySurfaceView(Context context) {
	super(context);
	sfh = this.getHolder();
	sfh.addCallback(this);
	paint = new Paint();
	paint.setColor(Color.WHITE);
	paint.setAntiAlias(true);
	setFocusable(true);
	//ʵ��GestureDetector
	gesture = new GestureDetector(this);
	//Ϊ��ǰ��ͼ���ô���������
	this.setOnTouchListener(this);
}

	/**
	 * SurfaceView��ͼ��������Ӧ�˺���
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
	}

	/**
	 * ��Ϸ��ͼ
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
	 * �����¼�����
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * �����¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ��Ϸ�߼�
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
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gesture.onTouchEvent(event);
	}

	//����
	@Override
	public boolean onDown(MotionEvent e) {
		Log.e("", "onDown");
		return false;
	}

	// ���ݰ���̧��
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.e("", "onSingleTapUp");
		return false;
	}

	// �ȶ��ݰ��� ��Ȼ�󻬶������ ̧��
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.e("", "onFling");
		//��ͼƬ���������һ���µ�iconλͼ
		vec.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon));
		return false;
	}

	// �ȶ��ݰ��� ��Ȼ�󻬶�
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.e("", "onScroll" + distanceX + "---" + distanceY);
		return false;
	}

	// �ȶ��ݰ��� ���̰�������
	@Override
	public void onShowPress(MotionEvent e) {
		Log.e("", "onShowPress");
	}

	// ����������
	@Override
	public void onLongPress(MotionEvent e) {
		Log.e("", "onLongPress");
	}
}
