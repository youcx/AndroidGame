package com.rp;

import android.content.Context;
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
	//��������Բ�ε����ĵ�������뾶
	private float smallCenterX = 120, smallCenterY = 120, smallCenterR = 20;
	private float BigCenterX = 120, BigCenterY = 120, BigCenterR = 40;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
		setFocusable(true);
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
				//���ƴ�Բ
				paint.setAlpha(0x77);
				canvas.drawCircle(BigCenterX, BigCenterY, BigCenterR, paint);
				//����СԲ
				canvas.drawCircle(smallCenterX, smallCenterY, smallCenterR, paint);

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
		//���û���ָ̧��Ӧ�ûָ�СԲ����ʼλ��
		if (event.getAction() == MotionEvent.ACTION_UP) {
			smallCenterX = BigCenterX;
			smallCenterY = BigCenterY;
		} else {
			int pointX = (int) event.getX();
			int pointY = (int) event.getY();
			//�ж��û������λ���Ƿ��ڴ�Բ��
			if (Math.sqrt(Math.pow((BigCenterX - (int) event.getX()), 2) + Math.pow((BigCenterY - (int) event.getY()), 2)) <= BigCenterR) {
				//��СԲ�����û�����λ���ƶ�
				smallCenterX = pointX;
				smallCenterY = pointY;
			} else {
				setSmallCircleXY(BigCenterX, BigCenterY, BigCenterR, getRad(BigCenterX, BigCenterY, pointX, pointY));
			}
		}
		return true;
	}

	/** 
	 * СԲ����ڴ�Բ��Բ���˶�ʱ������СԲ���ĵ������λ��
	 * @param centerX 
	 *            Χ�Ƶ�Բ��(��Բ)���ĵ�X����
	 * @param centerY 
	 *            Χ�Ƶ�Բ��(��Բ)���ĵ�Y����
	 * @param R
	 * 			     Χ�Ƶ�Բ��(��Բ)�뾶
	 * @param rad 
	 *            ��ת�Ļ��� 
	 */
	public void setSmallCircleXY(float centerX, float centerY, float R, double rad) {
		//��ȡԲ���˶���X����   
		smallCenterX = (float) (R * Math.cos(rad)) + centerX;
		//��ȡԲ���˶���Y����  
		smallCenterY = (float) (R * Math.sin(rad)) + centerY;
	}

	/**
	 * �����¼�����
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �õ�����֮��Ļ���
	 * @param px1    ��һ�����X����
	 * @param py1    ��һ�����Y����
	 * @param px2    �ڶ������X����
	 * @param py2    �ڶ������Y����
	 * @return
	 */
	public double getRad(float px1, float py1, float px2, float py2) {
		//�õ�����X�ľ���  
		float x = px2 - px1;
		//�õ�����Y�ľ���  
		float y = py1 - py2;
		//���б�߳�  
		float Hypotenuse = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//�õ�����Ƕȵ�����ֵ��ͨ�����Ǻ����еĶ��� ���ڱ�/б��=�Ƕ�����ֵ��  
		float cosAngle = x / Hypotenuse;
		//ͨ�������Ҷ����ȡ����ǶȵĻ���  
		float rad = (float) Math.acos(cosAngle);
		//��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180  
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
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
