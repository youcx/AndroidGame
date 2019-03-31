package com.mcp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	//����һ��iconλͼ
	private Bitmap bmpIcon;
	//��¼���������������
	private int x1, x2, y1, y2;
	//����
	private float rate = 1;
	//��¼�ϴεı���
	private float oldRate = 1;
	//��¼��һ�δ���ʱ�߶εĳ���
	private float oldLineDistance;
	//�ж��Ƿ�ͷ�ζ�ָ������Ļ
	private boolean isFirst = true;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		bmpIcon = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon);
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
				canvas.drawColor(Color.WHITE);
				canvas.save();
				//���Ż���(��ͼƬ���ĵ�������ţ�XY�����ű�����ͬ)
				canvas.scale(rate, rate, screenW / 2, screenH / 2);
				//����λͼicon
				canvas.drawBitmap(bmpIcon, screenW / 2 - bmpIcon.getWidth() / 2, screenH / 2 - bmpIcon.getHeight() / 2, paint);
				canvas.restore();
				//���ڹ۲죬���������������ʱ�γɵ��߶�
				canvas.drawLine(x1, y1, x2, y2, paint);
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
	//�û���ָ̧��Ĭ�ϻ�ԭΪ��һ�δ�����ʶλ�����ұ��汾�ε����ű���
	if (event.getAction() == MotionEvent.ACTION_UP) {
		isFirst = true;
		oldRate = rate;
	} else {
		x1 = (int) event.getX(0);
		y1 = (int) event.getY(0);
		x2 = (int) event.getX(1);
		y2 = (int) event.getY(1);
		if (event.getPointerCount() == 2) {
			if (isFirst) {
				//�õ���һ�δ���ʱ�߶εĳ���
				oldLineDistance = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2) + Math.pow(event.getY(1) - event.getY(0), 2));
				isFirst = false;
			} else {
				//�õ��ǵ�һ�δ���ʱ�߶εĳ���
				float newLineDistance = (float) Math.sqrt(Math.pow(event.getX(1) - event.getX(0), 2) + Math.pow(event.getY(1) - event.getY(0), 2));
				//��ȡ���ε����ű���
				rate = oldRate * newLineDistance / oldLineDistance;
			}
		}
	}
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
}
