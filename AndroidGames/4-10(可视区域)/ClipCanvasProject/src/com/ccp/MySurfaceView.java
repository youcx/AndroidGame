package com.ccp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
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
	private Bitmap bmp;

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
		//���û����޾��
		paint.setAntiAlias(true);
		//���ý���
		setFocusable(true);
		bmp = BitmapFactory.decodeResource(this.getResources(), R.drawable.image);
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
				//----���þ��ο�������
				//canvas.save();
				//canvas.clipRect(0, 0, 20, 20);
				//canvas.drawBitmap(bmp, 0, 0, paint);
				//canvas.restore();
				//----����Path���ÿ�������
				//canvas.save();
				//Path path = new Path();
				//path.addCircle(30, 30, 30, Direction.CCW);
				//canvas.clipPath(path);
				//canvas.drawBitmap(bmp, 0, 0, paint);
				//canvas.restore();
				//----����Region���ÿ�������
				canvas.save();
				Region region = new Region();
				region.op(new Rect(20,20,100,100), Region.Op.UNION);
				region.op(new Rect(40,20,80,150), Region.Op.XOR);
				canvas.clipRegion(region);
				canvas.drawBitmap(bmp, 0, 0, paint);
				canvas.restore();
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
