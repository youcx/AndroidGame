package com.tmb;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	// ----添加一个物理世界---->>
	final float RATE = 30;// 屏幕到现实世界的比例 30px：1m;
	World world;// 声明一个物理世界对象
	AABB aabb;// 声明一个物理世界的范围对象
	Vec2 gravity;// 声明一个重力向量对象
	float timeStep = 1f / 60f;// 物理世界模拟的的频率
	int iterations = 10;// 迭代值，迭代越大模拟越精确，但性能越低
	// -----图片
	Bitmap bmp;

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		this.setFocusable(true);
		// --添加一个物理世界--->>
		aabb = new AABB();// 实例化物理世界的范围对象
		gravity = new Vec2(0, 10);// 实例化物理世界重力向量对象
		aabb.lowerBound.set(-100, -100);// 设置物理世界范围的左上角坐标
		aabb.upperBound.set(100, 100);// 设置物理世界范围的右下角坐标
		world = new World(aabb, gravity, true);// 实例化物理世界对象
		// --图片Body的图片资源
		bmp = BitmapFactory
				.decodeResource(this.getResources(), R.drawable.himi);
		// ----在物理世界中添加多个自定义图片Body
		for (int i = 0; i < 3; i++) {
			createImageBody(bmp, 60 + i * 30, 10 + i * bmp.getHeight(), bmp
					.getWidth(), bmp.getHeight(), false);
		}
		createImageBody(bmp, 90, 200, bmp.getWidth(), bmp.getHeight(), true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createImageBody(Bitmap bmp, float x, float y, float width,
			float height, boolean isStatic) {
		// ---创建图片Body皮肤
		PolygonDef pd = new PolygonDef(); // 实例一个图片Body的皮肤
		if (isStatic) {
			pd.density = 0; // 设置图片Body为静态
		} else {
			pd.density = 1; // 设置图片Body的质量
		}
		pd.friction = 0.8f; // 设置图片Body的摩擦力
		pd.restitution = 0.3f; // 设置图片Body的恢复力
		// 设置图片Body快捷成盒子(矩形)
		// 两个参数为图片Body宽高的一半
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---创建刚体
		BodyDef bd = new BodyDef(); // 实例一个刚体对象
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// 设置刚体的坐标
		// ---创建Body（物体）
		Body body = world.createBody(bd); // 物理世界创建物体
		// 在body中保存自定义类
		body.m_userData = new BitmapBody(bmp, x, y);
		body.createShape(pd); // 为Body添加皮肤
		body.setMassFromShapes(); // 将整个物体计算打包
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				// 得到Body链表的表头
				Body body = world.getBodyList();
				// 通过world.getBodyCount()得到循环遍历Body的次数
				for (int i = 1; i < world.getBodyCount(); i++) {
					// 从body中获取其自定义的BitmapBody实例
					BitmapBody bb = (BitmapBody) body.m_userData;
					// 调用自定义图片类的draw方法
					bb.draw(canvas, paint);
					// 链表指向下一个body
					body = body.m_next;
				}
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
		// 得到Body链表的表头
		Body body = world.getBodyList();
		// 通过world.getBodyCount()得到循环遍历Body的次数
		for (int i = 1; i < world.getBodyCount(); i++) {
			// 从body中获取其自定义的BitmapBody实例
			BitmapBody bb = (BitmapBody) body.m_userData;
			// 得到当前body的角度
			float angele = (float) (body.getAngle() * 180 / Math.PI);
			// 得到当前body的质点X坐标
			float bodyX = body.getPosition().x * RATE - bb.getW() / 2;
			// 得到当前body的质点Y坐标
			float bodyY = body.getPosition().y * RATE - bb.getH() / 2; // 链表指向下一个body
			bb.setAngle(angele);
			bb.setX(bodyX);
			bb.setY(bodyY);
			// 链表指向下一个body
			body = body.m_next;
		}
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
