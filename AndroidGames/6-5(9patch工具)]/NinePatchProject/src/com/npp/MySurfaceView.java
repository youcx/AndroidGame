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
	//ԭͼ
	private Bitmap bmp_old;
	//ͨ��9patch�������ɵġ�.9.png��ͼ
	private Bitmap bmp_9path;
	//����NinePatch
	private NinePatch np;

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
		//��ͼƬ��Դ����λͼ
		bmp_old = BitmapFactory.decodeResource(getResources(), R.drawable.iamge);
		bmp_9path = BitmapFactory.decodeResource(getResources(), R.drawable.nine_image);
		//ʵ��NinePatchʵ��
		np = new NinePatch(bmp_9path, bmp_9path.getNinePatchChunk(), null);
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
			//---------������Ϊ�˸��õ�չʾ����������������
			RectF rectf_old_two = new RectF(0, 50, bmp_old.getWidth() * 2, 120 + bmp_old.getHeight() * 2); 
			RectF rectf_old_third = new RectF(0, 120 + bmp_old.getHeight() * 2, bmp_old.getWidth() * 3, 140 + bmp_old.getHeight() * 2 + bmp_old.getHeight()
					* 3);
			// --------�����Ƕ�����png�滭����-----------  
			canvas.drawBitmap(bmp_old, 0, 0, paint);
			canvas.drawBitmap(bmp_old, null, rectf_old_two, paint);
			canvas.drawBitmap(bmp_old, null, rectf_old_third, paint);
			RectF rectf_9path_two = new RectF(250, 50, 250 + bmp_9path.getWidth() * 2, 90 + bmp_9path.getHeight() * 2);
			RectF rectf_9path_third = new RectF(250, 120 + bmp_9path.getHeight() * 2, 250 + bmp_9path.getWidth() * 3, 140 + bmp_9path.getHeight() * 2
					+ bmp_9path.getHeight() * 3);
			canvas.drawBitmap(bmp_9path, 250, 0, paint);
			// --------������".9.png"ͼ��Ļ滭����-----------  
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
