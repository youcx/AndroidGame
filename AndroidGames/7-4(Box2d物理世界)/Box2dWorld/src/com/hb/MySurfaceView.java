package com.hb;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	// ----添加一个物理世界---->>
	final float RATE = 30;// 屏幕到现实世界的比例 30px：1m; 
	World world;// 声明一个物理世界对象
	AABB aabb;// 声明一个物理世界的范围对象
	Vec2 gravity;// 声明一个重力向量对象
	float timeStep = 1f / 60f;// 物理世界模拟的的频率
	int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低 
	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		// --添加一个物理世界--->>
		aabb = new AABB();// 实例化物理世界的范围对象
		gravity = new Vec2(0, 10);// 实例化物理世界重力向量对象
		aabb.lowerBound.set(-100, -100);// 设置物理世界范围的左上角坐标
		aabb.upperBound.set(100, 100);// 设置物理世界范围的右下角坐标
		world = new World(aabb, gravity, true);// 实例化物理世界对象 
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}
 
	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);

			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// --开始模拟物理世界--->>
		world.step(timeStep, iterations);// 物理世界进行模拟
	}

	public void run() {
		while (flag) {
			myDraw();
			Logic();
			try {
				Thread.sleep((long) timeStep * 1000);
			} catch (Exception ex) {
				Log.e("Himi", "Thread is Error!");
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

}
