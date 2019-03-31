package com.cp;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
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
	// ---添加物理世界----->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	final int iterations = 10;
	// ----在物理世界中添加一个多边形--->>
	Body body;// 声明物体对象
	float polygonX = 5;// 声明矩形X坐标
	float polygonY = 10;// 声明矩形Y坐标
	float polygonWidth = 50;// 声明矩形宽度
	float polygonHeight = 50;// 声明矩形高度
	// ----在物理世界中添加一个多边形--->>
	Body body2;// 声明物体对象
	float polygonX2 = 5;// 声明矩形X坐标
	float polygonY2 = 160;// 声明矩形Y坐标
	float polygonWidth2 = 50;// 声明矩形宽度
	float polygonHeight2 = 50;// 声明矩形高度

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		setFocusable(true);
		// ---添加物理世界-->>
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100f, -100f);
		aabb.upperBound.set(100f, 100f);
		world = new World(aabb, gravity, true);

		// ----在物理世界中添加一个多边形--->>
		body = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,
				false);// 创建一个多边形
		// ----在物理世界中添加一个多边形--->>
		body2 = createPolygon(polygonX2, polygonY2, polygonWidth2,
				polygonHeight2, true);// 创建一个多边形
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
		// ---创建多边形皮肤
		PolygonDef pd = new PolygonDef(); // 实例一个多边形的皮肤
		if (isStatic) {
			pd.density = 0; // 设置多边形为静态
		} else {
			pd.density = 1; // 设置多边形的质量
		}
		pd.friction = 0.8f; // 设置多边形的摩擦力
		pd.restitution = 0.3f; // 设置多边形的恢复力
		// 设置多边形快捷成盒子(矩形)
		// 两个参数为多边形宽高的一半
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		body.createShape(pd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				canvas.drawRect(polygonX, polygonY, polygonX + polygonWidth,
						polygonY + polygonHeight, paint);// 绘画矩形
				canvas.drawRect(polygonX2, polygonY2,
						polygonX2 + polygonWidth2, polygonY2 + polygonHeight2,
						paint);// 绘画矩形
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// ----物理世界进行模拟
		world.step(timeStep, iterations);
		Vec2 position = body.getPosition();
		polygonX = position.x * RATE - polygonWidth / 2;
		polygonY = position.y * RATE - polygonHeight / 2;
		Vec2 position2 = body2.getPosition();
		polygonX2 = position2.x * RATE - polygonWidth2 / 2;
		polygonY2 = position2.y * RATE - polygonHeight2 / 2;
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
