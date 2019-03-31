package com.spfp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
	//��¼��ǰԲ�����ھŹ����λ���±�
	private int creentTileIndex;
	//����һ��SharedPreferences����
	private SharedPreferences sp;

	/**
	 * SurfaceView��ʼ������
	 */
	public MySurfaceView(Context context) {
		super(context);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		setFocusable(true);
		//ͨ��Context��ȡSharedPreferenceʵ��
		sp = context.getSharedPreferences("SaveName", Context.MODE_PRIVATE);
		//ÿ�γ�������ʱ��ȡԲ�ε��±�
		int tempIndex = sp.getInt("CirCleIndex", -1);
		//�ж��������-1 ˵��û���ҵ����Ͳ��Ե�ǰ��¼Բ�εı������и�ֵ
		if (tempIndex != -1) {
			creentTileIndex = tempIndex;
		}
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
				paint.setColor(Color.BLACK);
				paint.setStyle(Style.STROKE);
				//���ƾŹ���(����Ļ�ŵȷ�)
				//�õ�ÿ������Ŀ��
				int tileW = screenW / 3;
				int tileH = screenH / 3;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						canvas.drawRect(i * tileW, j * tileH, (i + 1) * tileW, (j + 1) * tileH, paint);
					}
				}
				//���ݵõ���Բ���±�λ�ý��л�����Ӧ�ķ�����
				paint.setStyle(Style.FILL);
				canvas.drawCircle(creentTileIndex % 3 * tileW + tileW / 2, creentTileIndex / 3 * tileH + tileH / 2, 30, paint);
				//����˵��
				canvas.drawText("�ϼ���������Ϸ", 0, 20, paint);
				canvas.drawText("�¼�����ȡ��Ϸ", 110, 20, paint);
				canvas.drawText("���Ҽ����ƶ�Բ��", 215, 20, paint);
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
		//�ϼ�������Ϸ״̬
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			sp.edit().putInt("CirCleIndex", creentTileIndex).commit();
			//�¼���ȡ��Ϸ״̬
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			int tempIndex = sp.getInt("CirCleIndex", -1);
			if (tempIndex != -1) {
				creentTileIndex = tempIndex;
			}
			//Բ�ε��ƶ�
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (creentTileIndex > 0) {
				creentTileIndex -= 1;
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (creentTileIndex < 8) {
				creentTileIndex += 1;
			}
		}
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
