package com.mrc;

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
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//������������ͼ�εĿ������
	private int rectX1 = 10, rectY1 = 10, rectW1 = 40, rectH1 = 40;
	private int rectX2 = 100, rectY2 = 110, rectW2 = 40, rectH2 = 40;
	//���ڹ۲��Ƿ�������ײ����һ����ʶλ
	private boolean isCollsion;
	//�����һ�����εľ�����ײ����
	private Rect clipRect1 = new Rect(0, 0, 15, 15);
	private Rect clipRect2 = new Rect(rectW1 - 15, rectH1 - 15, rectW1, rectH1);
	private Rect[] arrayRect1 = new Rect[] { clipRect1, clipRect2 };
	//����ڶ������εľ�����ײ����
	private Rect clipRect3 = new Rect(0, 0, 15, 15);
	private Rect clipRect4 = new Rect(rectW2 - 15, rectH2 - 15, rectW2, rectH2);
	private Rect[] arrayRect2 = new Rect[] { clipRect3, clipRect4 };

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
				paint.setColor(Color.WHITE);
				paint.setStyle(Style.FILL);
				if (isCollsion) {
					paint.setTextSize(20);
					canvas.drawText("Collision��", 0, 30, paint);
				}
				//������������
				canvas.drawRect(rectX1, rectY1, rectX1 + rectW1, rectY1 + rectH1, paint);
				canvas.drawRect(rectX2, rectY2, rectX2 + rectW2, rectY2 + rectH2, paint);
				//---������ײ����ʹ�÷���䣬�����û�����ɫ��ɫ
				paint.setStyle(Style.STROKE);
				paint.setColor(Color.RED);
				//���Ƶ�һ�����ε����о�����ײ����
				for (int i = 0; i < arrayRect1.length; i++) {
					canvas.drawRect(arrayRect1[i].left + this.rectX1, arrayRect1[i].top + this.rectY1, arrayRect1[i].right + this.rectX1, arrayRect1[i].bottom
							+ this.rectY1, paint);
				}
				//���Ƶڶ������ε����о�����ײ����
				for (int i = 0; i < arrayRect2.length; i++) {
					canvas.drawRect(arrayRect2[i].left + this.rectX2, arrayRect2[i].top + this.rectY2, arrayRect2[i].right + this.rectX2, arrayRect2[i].bottom
							+ rectY2, paint);
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
		//�þ���1���Ŵ���λ���ƶ�
		rectX1 = (int) event.getX() - rectW1 / 2;
		rectY1 = (int) event.getY() - rectH1 / 2;
		if (isCollsionWithRect(arrayRect1, arrayRect2)) {
			isCollsion = true;
		} else {
			isCollsion = false;
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

	//Rect ���е��ĸ�����  top bottom left right
	//�ֱ��ʾ������ε�  	    ��       ��            ��          ��
	public boolean isCollsionWithRect(Rect[] rectArray, Rect[] rect2Array) {
		Rect rect = null;
		Rect rect2 = null;
		for (int i = 0; i < rectArray.length; i++) {
			//����ȡ����һ�����������ÿ������ʵ��
			rect = rectArray[i];
			//��ȡ����һ������������ÿ������Ԫ�ص�����ֵ
			int x1 = rect.left + this.rectX1;
			int y1 = rect.top + this.rectY1;
			int w1 = rect.right - rect.left;
			int h1 = rect.bottom - rect.top;
			for (int j = 0; j < rect2Array.length; j++) {
				//����ȡ���ڶ������������ÿ������ʵ��
				rect2 = rect2Array[j];
				//��ȡ���ڶ�������������ÿ������Ԫ�ص�����ֵ
				int x2 = rect2.left + this.rectX2;
				int y2 = rect2.top + this.rectY2;
				int w2 = rect2.right - rect2.left;
				int h2 = rect2.bottom - rect2.top;
				//����ѭ����������������ײ��������Ԫ��֮���λ�ù�ϵ
				if (x1 >= x2 && x1 >= x2 + w2) {
				} else if (x1 <= x2 && x1 + w1 <= x2) {
				} else if (y1 >= y2 && y1 >= y2 + h2) {
				} else if (y1 <= y2 && y1 + h1 <= y2) {
				} else {
					//ֻҪ��һ����ײ������������һ��ײ�������鷢����ײ����Ϊ��ײ
					return true;
				}
			}
		}
		return false;
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
