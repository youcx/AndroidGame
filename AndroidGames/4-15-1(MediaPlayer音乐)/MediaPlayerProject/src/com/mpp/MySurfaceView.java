package com.mpp;

import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
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
public class MySurfaceView extends SurfaceView implements Callback, Runnable,OnCompletionListener {
	private SurfaceHolder sfh;
	private Paint paint;
	private Thread th;
	private boolean flag;
	private Canvas canvas;
	private int screenW, screenH;
	//�������ֵ�״̬����
	private final int MEDIAPLAYER_PAUSE = 0;//��ͣ
	private final int MEDIAPLAYER_PLAY = 1;//������
	private final int MEDIAPLAYER_STOP = 2;//ֹͣ
	//���ֵĵ�ǰ��״̬
	private int mediaSate = 0;
	//����һ�����ֲ�����
	private MediaPlayer mediaPlayer;
	//��ǰ���ֲ��ŵ�ʱ���
	private int currentTime;
	//��ǰ���ֵ���ʱ��
	private int musicMaxTime;
	//��ǰ���ֵ�������С
	private int currentVol;
	//���������ʱ���
	private int setTime = 5000;
	//������������
	private AudioManager am;

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
		//ʵ�����ֲ�����
		mediaPlayer = MediaPlayer.create(context, R.raw.bgmusic);
		//����ѭ������(������ѭ������OnCompletionListener���������޷����������Ƿ񲥷����)
		//mediaPlayer.setLooping(true);//����ѭ������  
		//��ȡ�����ļ�����ʱ��
		musicMaxTime = mediaPlayer.getDuration();
		//ʵ��������
		am = (AudioManager) MainActivity.instance.getSystemService(Context.AUDIO_SERVICE);
		//���õ�ǰ����������Сֻ�����ý�����ֽ��е���
		MainActivity.instance.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		//��������ɼ�����
		mediaPlayer.setOnCompletionListener(this);
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
				paint.setColor(Color.RED);
				paint.setTextSize(15);
				canvas.drawText("��ǰ����: " + currentVol, 50, 40, paint);
				canvas.drawText("��ǰ���ŵ�ʱ��(����)/��ʱ��(����)", 50, 70, paint);
				canvas.drawText(currentTime + "/" + musicMaxTime, 100, 100, paint);
				canvas.drawText("������м䰴ť�л� ��ͣ/��ʼ", 50, 130, paint);
				canvas.drawText("�������������" + setTime / 1000 + "�� ", 50, 160, paint);
				canvas.drawText("������������" + setTime / 1000 + "�� ", 50, 190, paint);
				canvas.drawText("����������������� ", 50, 220, paint);
				canvas.drawText("�����������С����", 50, 250, paint);
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
		//�����м�����/��ͣ����
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			try {
				switch (mediaSate) {
				//��ǰ���ڲ��ŵ�״̬
				case MEDIAPLAYER_PLAY:
					mediaPlayer.pause();
					mediaSate = MEDIAPLAYER_PAUSE;
					break;
				//��ǰ������ͣ��״̬
				case MEDIAPLAYER_PAUSE:
					mediaPlayer.start();
					mediaSate = MEDIAPLAYER_PLAY;
					break;
				//��ǰ����ֹͣ��״̬
				case MEDIAPLAYER_STOP:
					/*ʹ��android MediaPlayer����һ������ʱ����ʱ�����prepareasync called in state 8����
					���·������Ա�������쳣����,   //�ڲ���֮ǰ���ж�playerMusic�Ƿ�ռ�ã������Ͳ��ᱨ����*/
					if (mediaPlayer != null) {
						mediaPlayer.pause();
						mediaPlayer.stop();
					}
					mediaPlayer.prepare();
					mediaPlayer.start();
					mediaSate = MEDIAPLAYER_PLAY;
					break;
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//�����ϼ��������ֲ����������
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol + 1, AudioManager.FLAG_PLAY_SOUND);
			//�����¼��������ֲ���������С
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentVol - 1, AudioManager.FLAG_PLAY_SOUND);
			//��������������ֲ���ʱ�䵹������			
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			if (currentTime - setTime <= 0) {
				mediaPlayer.seekTo(0);
			} else {
				mediaPlayer.seekTo(currentTime - setTime);
			}
			//�����Ҽ��������ֲ���ʱ��������		
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			if (currentTime + setTime >= musicMaxTime) {
				mediaPlayer.seekTo(musicMaxTime);
			} else {
				mediaPlayer.seekTo(currentTime + setTime);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ��Ϸ�߼�
	 */
	private void logic() {
		if (mediaPlayer != null) {
			//��ȡ��ǰ���ֲ��ŵ�ʱ��
			currentTime = mediaPlayer.getCurrentPosition();
			//��ȡ��ǰ������ֵ  
			currentVol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			//��ȡ��ǰ���������ֵ  
			//int valueMax = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		} else {
			currentTime = 0;
		}
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
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}
	/**
	 * ���������Ƿ񲥷����
	 */
	@Override
	public void onCompletion(MediaPlayer arg0) {
		if (mediaPlayer == arg0) {
			Log.v("Himi", "Play Completed");
		}
	}
}
