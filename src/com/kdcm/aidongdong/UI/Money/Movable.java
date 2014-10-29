package com.kdcm.aidongdong.UI.Money;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Movable {
	int startX = 0; // 初始X坐标
	int startY = 0; // 初始Y坐标
	int currentX; // 实时X坐标
	int currentY; // 实时Y坐标
	float startVX = 0f; // 初始水平方向速度
	float startVY = 200f; // 初始垂直方向速度
	float currentVX = 0f; // 实时水平方向速度
	float currentVY = 0f; // 实时垂直方向速度
	int radius; // 物体半径
	double startTime_X; // 水平方向运动起始时间
	double startTime_Y; // 垂直方向运动起始时间
	Bitmap bitmap = null; //
	public static BallThread ballThread = null; // 负责小球移动的线程
	boolean bFall = false; // 小球是否已从木板落下
	boolean moveRight = true; // 标志小球向左弹还是向右弹默认向右移动
	float impactFactor = 0.2f; // 小球撞地后速度损失系数

	public Movable(int x, int y, int radius, int windowWidth, Bitmap bitmap) {
		this.startX = x;
		this.currentX = x;
		this.startY = y;
		this.currentY = y;
		this.radius = radius;
		this.bitmap = bitmap;
		this.startTime_X = System.nanoTime(); // 1秒=10亿纳秒
		this.startVX = BallView.VX_MIN
				+ (int) ((BallView.VX_MAX - BallView.VX_MIN) * Math.random());
		// this.startVX = 50;
		this.currentVX = startVX;
		ballThread = new BallThread(this, bitmap.getWidth(), windowWidth);
		ballThread.start();

	}

	public void drawSelf(Canvas canvas) {
		// Log.e(currentX+"", currentY+"");
		canvas.drawBitmap(bitmap, currentX, currentY, null);
	}
}
