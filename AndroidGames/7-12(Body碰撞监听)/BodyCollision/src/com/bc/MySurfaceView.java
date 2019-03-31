package com.bc;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.collision.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.ContactFilter;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MySurfaceView extends SurfaceView implements Callback, Runnable, ContactListener, ContactFilter {
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
	// --->>给第一个Body赋予力
	Body body1, body2;

	/* Shape 里的 m_isSensor 属性表示发生碰撞但是不产生碰撞效果 */
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
		// ----在物理世界中添加两个动态圆形Body
		body1 = createCircle(39, 17, 10, false);
		body2 = createCircle(30, 47, 10, false);
		body1.getShapeList().getFilterData().groupIndex = 1;// 定义body1分组1
		body1.getShapeList().getFilterData().maskBits = 2;// 指定body1碰撞种类为2
		body2.getShapeList().getFilterData().groupIndex = 2;// 定义body2分组2
		body2.getShapeList().getFilterData().categoryBits = 2;// 定义body2种类为2
		// 添加屏幕下方添加多个静态物体
		for (int i = 0; i < 5; i++) {
			Body body = createCircle(i * 20, 100, 10, true);
			body.getShapeList().getFilterData().groupIndex = 3;
			body.getShapeList().getFilterData().categoryBits = 4;// 定义静态body种类为4
		}
		//为物理世界设置碰撞监听
		world.setContactListener(this);
		// 碰撞筛选器比监听器更加的具有扩展性，可以在筛选器中实现监听器的功能；
		// 但是由于自由度太大，一般不推荐使用
		//	world.setContactFilter(this);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createCircle(float x, float y, float r, boolean isStatic) {
		CircleDef cd = new CircleDef();
		if (isStatic) {
			cd.density = 0;
		} else {
			cd.density = 1;
		}
		cd.friction = 0.8f;
		cd.restitution = 0.3f;
		cd.radius = r / RATE;
		BodyDef bd = new BodyDef();
		bd.position.set((x + r) / RATE, (y + r) / RATE);
		Body body = world.createBody(bd);
		body.m_userData = new MyCircle(x, y, r);
		body.createShape(cd);
		body.setMassFromShapes();
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				Body body = world.getBodyList();
				for (int i = 1; i < world.getBodyCount(); i++) {
					((MyCircle) body.m_userData).draw(canvas, paint);
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
		// 取出body链表表头
		Body body = world.getBodyList();
		for (int i = 1; i < world.getBodyCount(); i++) {
			// 设置MyCircle的X，Y坐标
			MyCircle mc = (MyCircle) body.m_userData;
			mc.setX(body.getPosition().x * RATE - mc.r);
			mc.setY(body.getPosition().y * RATE - mc.r);
			// 将链表指针指向下一个body元素
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

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}

	// ----->>ContactListener监听器
	@Override
	public void add(ContactPoint arg0) {
		//每当有碰撞会调用此函数添加新的接触点
	}

	@Override
	public void persist(ContactPoint arg0) {
		// 持续碰撞调用此函数
	}

	@Override
	public void remove(ContactPoint arg0) {
		//脱离碰撞调用此函
	}

	@Override
	public void result(ContactResult arg0) {
		// 发生碰撞（有新的接触点被监听到）会调用此函数
		// 持续碰撞时也会调用此函数
	}

	// -----碰撞筛选器
	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		return false;
	}
}
