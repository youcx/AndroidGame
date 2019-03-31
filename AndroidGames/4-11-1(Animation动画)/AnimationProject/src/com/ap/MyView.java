/**
 * 
 */
package com.ap;

import com.ap.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

/**
 *@author Himi
 *@AlphaAnimation 渐变透明度动画效果
 *@ScaleAnimation 渐变尺寸伸缩动画效果
 *@TranslateAnimation 画面转换位置移动动画效果
 *@RotateAnimation 画面转移旋转动画效果
 */
public class MyView extends View implements AnimationListener {
	public static MyView mv;
	private Paint paint;
	private Bitmap bmp;
	private int x = 50;
	private Animation mAlphaAnimation;
	private Animation mScaleAnimation;
	private Animation mTranslateAnimation;
	private Animation mRotateAnimation;

	public MyView(Context context) {
		super(context);
		mv = this;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		this.setFocusable(true);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//黑色背景
		canvas.drawColor(Color.BLACK);
		canvas.drawText("方向键↑ 渐变透明度动画效果", 80, this.getHeight() - 80, paint);
		canvas.drawText("方向键↓ 渐变尺寸伸缩动画效果", 80, this.getHeight() - 60, paint);
		canvas.drawText("方向键← 画面转换位置移动动画效果", 80, this.getHeight() - 40, paint);
		canvas.drawText("方向键→ 画面转移旋转动画效果", 80, this.getHeight() - 20, paint);
		//绘制位图icon
		canvas.drawBitmap(bmp, this.getWidth() / 2 - bmp.getWidth() / 2, this.getHeight() / 2 - bmp.getHeight() / 2, paint);
		x += 1;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {// 渐变透明度动画效果
			mAlphaAnimation = new AlphaAnimation(0.1f, 1.0f);
			mAlphaAnimation.setAnimationListener(this);
			mAlphaAnimation.setDuration(3000);
			// //设置时间持续时间为3000 毫秒=3秒
			this.startAnimation(mAlphaAnimation);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {// 渐变尺寸伸缩动画效果
			mScaleAnimation = new ScaleAnimation(0.0f, 2.0f, 1.5f, 1.5f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.0f);
			mScaleAnimation.setAnimationListener(this);
			mScaleAnimation.setDuration(2000);
			this.startAnimation(mScaleAnimation);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {// 画面转换位置移动动画效果
			mTranslateAnimation = new TranslateAnimation(0, 100, 0, 100);
			mTranslateAnimation.setAnimationListener(this);
			mTranslateAnimation.setDuration(2000);
			this.startAnimation(mTranslateAnimation);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {// 画面转移旋转动画效果
			mRotateAnimation = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			mRotateAnimation.setAnimationListener(this);
			mRotateAnimation.setDuration(3000);
			this.startAnimation(mRotateAnimation);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onAnimationStart(Animation animation) {
		Log.e("", "AnimationStart!");

	}
	@Override
	public void onAnimationEnd(Animation animation) {
		Log.e("", "AnimationEnd!");

	}
	@Override
	public void onAnimationRepeat(Animation animation) {
		Log.e("", "AnimationRepeat!");
	}
}
