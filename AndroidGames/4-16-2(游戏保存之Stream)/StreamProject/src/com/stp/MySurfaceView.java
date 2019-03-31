package com.stp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Environment;
import android.util.Log;
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
		//�õ��Ķ�����д����
		FileOutputStream fos = null;
		FileInputStream fis = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		//�ϼ�������Ϸ״̬
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			try {
				// ��SDcard��д������
				// ��̽�ն��Ƿ���sdcard! ����̽��SDCard�Ƿ��ڱ��Ƴ���״̬
				if (Environment.getExternalStorageState() != null && !Environment.getExternalStorageState().equals("removed")) {
					Log.v("Himi", "д�룬��SD��");
					File path = new File("/sdcard/himi");// ����Ŀ¼  
					File f = new File("/sdcard/himi/save.himi");// �����ļ�  
					if (!path.exists()) {// Ŀ¼���ڷ���true  
						path.mkdirs();// ����һ��Ŀ¼  
					}
					if (!f.exists()) {// �ļ����ڷ���true  
						f.createNewFile();// ����һ���ļ�  
					}
					fos = new FileOutputStream(f);// �����ݴ���sd����  
				} else {
					//Ĭ��ϵͳ·��
					//����Activityʵ�������ļ��õ�һ��д����
					fos = MainActivity.instance.openFileOutput("save.himi", Context.MODE_PRIVATE);
				}
				//��д������װ������д������
				dos = new DataOutputStream(fos);
				//д��һ��int����(��Բ�����ڸ��ӵ��±�д�����ļ���)
				dos.writeInt(creentTileIndex);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//��ʹ����ʱ�����쳣��ҲҪ�ر���
				try {
					if (fos != null)
						fos.close();
					if (dos != null)
						dos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//�¼���ȡ��Ϸ״̬
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			boolean isHaveSDCard = false;
			// ��SDcard�ж�ȡ����
			// ��̽�ն��Ƿ���sdcard! ����̽��SDCard�Ƿ��ڱ��Ƴ���״̬
			if (Environment.getExternalStorageState() != null && !Environment.getExternalStorageState().equals("removed")) { 
				Log.v("Himi", "��ȡ����SD��");
				isHaveSDCard = true;
			}
			try {
				if (isHaveSDCard) {
					File path = new File("/sdcard/himi");// ����Ŀ¼  
					File f = new File("/sdcard/himi/save.himi");// �����ļ�  
					if (!path.exists()) {// Ŀ¼���ڷ���true
						return false;
					} else {
						if (!f.exists()) {// �ļ����ڷ���true  
							return false;
						}
					}
					fis = new FileInputStream(f);// �����ݴ���sd����  
				} else {
					if (MainActivity.instance.openFileInput("save.himi") != null) {
						//����Activityʵ�������ļ��õ�һ��������
						fis = MainActivity.instance.openFileInput("save.himi");
					}
				}
				//����������װ�����ݶ�������
				dis = new DataInputStream(fis);
				//����һ��Int���͸�ֵ��Բ�����ڸ��ӵ��±�
				creentTileIndex = dis.readInt();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//��ʹ��ȡʱ�����쳣��ҲҪ�ر���
				try {
					if (fis != null)
						fis.close();
					if (dis != null)
						dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
