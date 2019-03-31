package com.cp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
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
	//���ڿ���SurfaceView
	private SurfaceHolder sfh;
	//����һ������
	private Paint paint;
	//����һ���߳�
	private Thread th;
	//�߳������ı�ʶλ
	private boolean flag;
	//����һ������
	private Canvas canvas;
	//������Ļ�Ŀ��
	private int screenW, screenH;
	//���û�����ͼ�޾��
	private PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		//ʵ��SurfaceHolder
		sfh = this.getHolder();
		//ΪSurfaceView���״̬����
		sfh.addCallback(this);
		//ʵ��һ������
		paint = new Paint();
		//���û�����ɫΪ��ɫ
		paint.setColor(Color.WHITE);
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
			//----���û�����ͼ�޾��
			canvas.setDrawFilter(pfd);
			//----������仭����ˢ��
			canvas.drawColor(Color.BLACK);
			//----�����ı�
			canvas.drawText("drawText", 10, 10, paint);
			//----�������ص�
			canvas.drawPoint(10, 20, paint);
			//----���ƶ�����ص�
			canvas.drawPoints(new float[] { 10, 30, 30, 30 }, paint);
			//----����ֱ��
			canvas.drawLine(10, 40, 50, 40, paint);
			//----���ƶ���ֱ��
			canvas.drawLines(new float[] { 10, 50, 50, 50, 70, 50, 110, 50 }, paint);
			//----���ƾ���
			canvas.drawRect(10, 60, 40, 100, paint);
			//----���ƾ���2
			Rect rect = new Rect(10, 110, 60, 130);
			canvas.drawRect(rect, paint);
			canvas.drawRect(rect, paint);
			//----����Բ�Ǿ���
			RectF rectF = new RectF(10, 140, 60, 170);
			canvas.drawRoundRect(rectF, 20, 20, paint);
			//----����Բ��
			canvas.drawCircle(20, 200, 20, paint);
			//----���ƻ���
			canvas.drawArc(new RectF(150, 20, 200, 70), 0, 230, true, paint);
			//----������Բ
			canvas.drawOval(new RectF(150, 80, 180, 100), paint);
			//----����ָ��·��ͼ��
			Path path = new Path();
			//����·�����
			path.moveTo(160, 150);
			//·��1
			path.lineTo(200, 150);
			//·��2
			path.lineTo(180, 200);
			//·������
			path.close();
			canvas.drawPath(path, paint);
			//----����ָ��·��ͼ��
			Path pathCircle = new Path();
			//���һ��Բ�ε�·��
			pathCircle.addCircle(130, 260, 20, Path.Direction.CCW);
			//----���ƴ�Բ�ε�·���ı�
			canvas.drawTextOnPath("PathText", pathCircle, 10, 20, paint);
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
