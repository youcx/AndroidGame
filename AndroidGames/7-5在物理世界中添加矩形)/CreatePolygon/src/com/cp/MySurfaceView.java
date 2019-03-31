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
	// ---�����������----->>
	final float RATE = 30;
	World world;
	AABB aabb;
	Vec2 gravity;
	float timeStep = 1f / 60f;
	final int iterations = 10;
	// ----���������������һ�������--->>
	Body body;// �����������
	float polygonX = 5;// ��������X����
	float polygonY = 10;// ��������Y����
	float polygonWidth = 50;// �������ο��
	float polygonHeight = 50;// �������θ߶�
	// ----���������������һ�������--->>
	Body body2;// �����������
	float polygonX2 = 5;// ��������X����
	float polygonY2 = 160;// ��������Y����
	float polygonWidth2 = 50;// �������ο��
	float polygonHeight2 = 50;// �������θ߶�

	public MySurfaceView(Context context) {
		super(context);
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		setFocusable(true);
		// ---�����������-->>
		aabb = new AABB();
		gravity = new Vec2(0f, 10f);
		aabb.lowerBound.set(-100f, -100f);
		aabb.upperBound.set(100f, 100f);
		world = new World(aabb, gravity, true);

		// ----���������������һ�������--->>
		body = createPolygon(polygonX, polygonY, polygonWidth, polygonHeight,
				false);// ����һ�������
		// ----���������������һ�������--->>
		body2 = createPolygon(polygonX2, polygonY2, polygonWidth2,
				polygonHeight2, true);// ����һ�������
	}

	public void surfaceCreated(SurfaceHolder holder) {
		flag = true;
		th = new Thread(this);
		th.start();
	}

	public Body createPolygon(float x, float y, float width, float height,
			boolean isStatic) {
		// ---���������Ƥ��
		PolygonDef pd = new PolygonDef(); // ʵ��һ������ε�Ƥ��
		if (isStatic) {
			pd.density = 0; // ���ö����Ϊ��̬
		} else {
			pd.density = 1; // ���ö���ε�����
		}
		pd.friction = 0.8f; // ���ö���ε�Ħ����
		pd.restitution = 0.3f; // ���ö���εĻָ���
		// ���ö���ο�ݳɺ���(����)
		// ��������Ϊ����ο�ߵ�һ��
		pd.setAsBox(width / 2 / RATE, height / 2 / RATE);
		// ---��������
		BodyDef bd = new BodyDef(); // ʵ��һ���������
		bd.position.set((x + width / 2) / RATE, (y + height / 2) / RATE);// ���ø��������
		// ---����Body�����壩
		Body body = world.createBody(bd); // �������紴������
		body.createShape(pd); // ΪBody���Ƥ��
		body.setMassFromShapes(); // ���������������
		return body;
	}

	public void myDraw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				canvas.drawRect(polygonX, polygonY, polygonX + polygonWidth,
						polygonY + polygonHeight, paint);// �滭����
				canvas.drawRect(polygonX2, polygonY2,
						polygonX2 + polygonWidth2, polygonY2 + polygonHeight2,
						paint);// �滭����
			}
		} catch (Exception e) {
			Log.e("Himi", "myDraw is Error!");
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}

	public void Logic() {
		// ----�����������ģ��
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
