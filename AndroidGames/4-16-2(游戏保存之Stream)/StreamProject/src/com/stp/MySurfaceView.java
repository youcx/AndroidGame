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
	//记录当前圆形所在九宫格的位置下标
	private int creentTileIndex;

	/**
	 * SurfaceView初始化函数
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
	 * SurfaceView视图创建，响应此函数
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
		th.start();
	}

	/**
	 * 游戏绘图
	 */
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				paint.setColor(Color.BLACK);
				paint.setStyle(Style.STROKE);
				//绘制九宫格(将屏幕九等份)
				//得到每个方格的宽高
				int tileW = screenW / 3;
				int tileH = screenH / 3;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						canvas.drawRect(i * tileW, j * tileH, (i + 1) * tileW, (j + 1) * tileH, paint);
					}
				}
				//根据得到的圆形下标位置进行绘制相应的方格中
				paint.setStyle(Style.FILL);
				canvas.drawCircle(creentTileIndex % 3 * tileW + tileW / 2, creentTileIndex / 3 * tileH + tileH / 2, 30, paint);
				//操作说明
				canvas.drawText("上键：保存游戏", 0, 20, paint);
				canvas.drawText("下键：读取游戏", 110, 20, paint);
				canvas.drawText("左右键：移动圆形", 215, 20, paint);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	/**
	 * 触屏事件监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	/**
	 * 按键事件监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//用到的读出、写入流
		FileOutputStream fos = null;
		FileInputStream fis = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		//上键保存游戏状态
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			try {
				// 从SDcard中写入数据
				// 试探终端是否有sdcard! 并且探测SDCard是否处于被移除的状态
				if (Environment.getExternalStorageState() != null && !Environment.getExternalStorageState().equals("removed")) {
					Log.v("Himi", "写入，有SD卡");
					File path = new File("/sdcard/himi");// 创建目录  
					File f = new File("/sdcard/himi/save.himi");// 创建文件  
					if (!path.exists()) {// 目录存在返回true  
						path.mkdirs();// 创建一个目录  
					}
					if (!f.exists()) {// 文件存在返回true  
						f.createNewFile();// 创建一个文件  
					}
					fos = new FileOutputStream(f);// 将数据存入sd卡中  
				} else {
					//默认系统路径
					//利用Activity实例打开流文件得到一个写入流
					fos = MainActivity.instance.openFileOutput("save.himi", Context.MODE_PRIVATE);
				}
				//将写入流封装在数据写入流中
				dos = new DataOutputStream(fos);
				//写入一个int类型(将圆形所在格子的下标写入流文件中)
				dos.writeInt(creentTileIndex);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//即使保存时发生异常，也要关闭流
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
			//下键读取游戏状态
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			boolean isHaveSDCard = false;
			// 从SDcard中读取数据
			// 试探终端是否有sdcard! 并且探测SDCard是否处于被移除的状态
			if (Environment.getExternalStorageState() != null && !Environment.getExternalStorageState().equals("removed")) { 
				Log.v("Himi", "读取，有SD卡");
				isHaveSDCard = true;
			}
			try {
				if (isHaveSDCard) {
					File path = new File("/sdcard/himi");// 创建目录  
					File f = new File("/sdcard/himi/save.himi");// 创建文件  
					if (!path.exists()) {// 目录存在返回true
						return false;
					} else {
						if (!f.exists()) {// 文件存在返回true  
							return false;
						}
					}
					fis = new FileInputStream(f);// 将数据存入sd卡中  
				} else {
					if (MainActivity.instance.openFileInput("save.himi") != null) {
						//利用Activity实例打开流文件得到一个读入流
						fis = MainActivity.instance.openFileInput("save.himi");
					}
				}
				//将读入流封装在数据读入流中
				dis = new DataInputStream(fis);
				//读出一个Int类型赋值与圆形所在格子的下标
				creentTileIndex = dis.readInt();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				//即使读取时发生异常，也要关闭流
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
	 * 游戏逻辑
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
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
