package com.cc;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
	// ----在物理世界中添加一个圆形--->>
	Body body;// 声明物体对象
	float circleX = 0;// 声明圆形X坐标
	float circleY = 0;// 声明圆形Y坐标
	float circleR = 20;// 声明圆形半径
	// ----在物理世界中添加一个多边形--->>
	Body body2;// 声明物体对象
	float polygonX = 5;// 声明矩形X坐标
	float polygonY = 110;// 声明矩形Y坐标
	float polygonWidth = 150;// 声明矩形宽度
	float polygonHeight = 50;// 声明矩形高度
	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setAntiAlias(true);
		setFocusable(true);
		// ---添加物理世界-->>
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100f, -100f);
		aabb.upperBound.set(100f, 100f);
		world = new World(aabb, gravity, true);

		// ----在物理世界中添加一个圆形--->>
		body = createCircle(circleX, circleY, circleR, false);// 创建一个圆形
		// ----在物理世界中添加一个多边形--->>
		body2 = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createCircle(float x, float y, float r, boolean isStatic) {
		// ---创建圆形皮肤
		CircleDef cd = new CircleDef(); // 实例一个圆形的皮肤
		if (isStatic) {
			cd.density = 0; // 设置圆形为静态
		} else {
			cd.density = 1; // 设置圆形的质量
		}
		cd.friction = 0.8f; // 设置圆形的摩擦力
		cd.restitution = 0.3f; // 设置圆形的恢复力
		cd.radius = r / RATE; // 设置圆形的半径
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + r) / RATE, (y + r) / RATE); // 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		body.createShape(cd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
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
				RectF rect = new RectF(circleX, circleY, circleX + circleR * 2,
						circleY + circleR * 2);
				canvas.drawArc(rect, 0, 360, true, paint);// 绘画圆形
				canvas.drawRect(polygonX, polygonY, polygonX + polygonWidth,
						polygonY + polygonHeight, paint);
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
		circleX = position.x * RATE - circleR;
		circleY = position.y * RATE - circleR;
		Vec2 position2 = body2.getPosition();
		polygonX = position2.x * RATE - polygonWidth / 2;
		polygonY = position2.y * RATE - polygonHeight / 2;
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
