package com.pp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
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
	//���ڿ���SurfaceView
	private SurfaceHolder sfh;
	//����һ���߳�
	private Thread th;
	//�߳������ı�ʶλ
	private boolean flag;
	//����һ������
	private Canvas canvas;
	//������Ļ�Ŀ��
	private int screenW, screenH;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		//ʵ��SurfaceHolder
		sfh = this.getHolder();
		//ΪSurfaceView���״̬����
		sfh.addCallback(this);
		//���ý���
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
				//-----���û����޾��
				Paint paint1 = new Paint();
				canvas.drawCircle(40, 30, 20, paint1);
				paint1.setAntiAlias(true);
				canvas.drawCircle(100, 30, 20, paint1);
				//-----���û��ʵ�͸����
				canvas.drawText("��͸����", 100, 70, new Paint());
				Paint paint2 = new Paint();
				paint2.setAlpha(0x77);
				canvas.drawText("��͸����", 20, 70, paint2);
				//-----���û����ı���ê��
				canvas.drawText("ê��", 20, 90, new Paint());
				Paint paint3 = new Paint();
				//�������ı������ĵ����
				paint3.setTextAlign(Paint.Align.CENTER);
				canvas.drawText("ê��", 20, 105, paint3);
				//------��ȡ�ı��ĳ���
				Paint paint4 = new Paint();
				float len =paint4.measureText("�ı����:");
				canvas.drawText("�ı�����:"+len, 20, 130, new Paint());
				//------���û�����ʽ
				canvas.drawRect(new Rect(20,140,40,160), new Paint());
				Paint paint5 = new Paint();
				//���û��ʲ����
				paint5.setStyle(Style.STROKE);
				canvas.drawRect(new Rect(60,140,80,160), paint5);
				//------���û�����ɫ
				Paint paint6 = new Paint();
				paint6.setColor(Color.GRAY);
				canvas.drawText("��ɫ", 30, 180, paint6);
				//------���û��ʵĴ�ϸ�̶�
				canvas.drawLine(20, 200,70, 200, new Paint());
				Paint paint7 = new Paint();
				paint7.setStrokeWidth(7);
				canvas.drawLine(20, 220,70, 220,paint7);
				//------���û��ʻ����ı��������ϸ
				Paint paint8 = new Paint();
				paint8.setTextSize(20);
				canvas.drawText("���ֳߴ�", 20, 260, paint8);
				//------���û��ʵ�ARGB����
				Paint paint9 = new Paint();
				paint9.setARGB(0x77, 0xff, 0x00, 0x00);
				canvas.drawText("��ɫ��͸��", 20, 290, paint9);
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
}
