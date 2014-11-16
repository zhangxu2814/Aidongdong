package com.kdcm.aidongdong.UI.Money;

import android.util.Log;

public class BallThread extends Thread {

	Movable movable;
	boolean flag = false; // 线程执行标志位
	int sleepSpan = 40; // 线程休眠时间
	float g = 5000f; // 加速度
	double currentTime; // 当前时间
	int bitmapWidth;
	int windowWidth;

	public BallThread(Movable movable, int bitmapWidth, int windowWidth) {
		super();
		this.movable = movable;
		this.flag = true;
		this.bitmapWidth = bitmapWidth;
		this.windowWidth = windowWidth;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (flag) {
			currentTime = System.nanoTime();
			// 水平方向走过的时间
			double timeSpan_X = (double) (currentTime - movable.startTime_X) / 1000 / 1000 / 1000;
			/*
			 * ==================================================================
			 * ===================
			 */
			// 判断小球是否已弹到屏幕边缘，弹到右边缘后则改向左弹，弹到左边缘后则改向右弹
			if (movable.moveRight) {
				if (movable.currentX + bitmapWidth >= windowWidth) {
					movable.moveRight = false;
					movable.currentX = (int) (movable.startX - movable.currentVX
							* timeSpan_X);
				} else {
					movable.currentX = (int) (movable.startX + movable.currentVX
							* timeSpan_X);
				}
			} else {
				if (movable.currentX <= 0) {
					movable.moveRight = true;
					movable.currentX = (int) (movable.startX + movable.currentVX
							* timeSpan_X);
				} else {
					movable.currentX = (int) (movable.startX - movable.currentVX
							* timeSpan_X);
				}
			}
			/*
			 * ==================================================================
			 * ===================
			 */
			// 当前X坐标
			// movable.currentX = (int)
			// (movable.startX+movable.currentVX*timeSpan_X);
			if (movable.bFall) { // 球正在下落
				// 垂直方向走过的时间
				double timeSpan_Y = (double) (currentTime - movable.startTime_Y) / 1000 / 1000 / 1000;
				// 当前Y坐标，
				movable.currentY = (int) (movable.startY + movable.startVY
						* timeSpan_Y + timeSpan_Y * timeSpan_Y * g / 2);
				// 当前垂直方向速度
				movable.currentVY = (float) (movable.startVY + timeSpan_Y * g);
				// 判断球是否达到最高点
				if (movable.startVY < 0
						&& Math.abs(movable.currentVY) <= BallView.UP_ZERO) {
					movable.startTime_Y = System.nanoTime();
					movable.startVY = 0;
					movable.currentVY = 0;
					movable.startY = movable.currentY;
				}

				// 判断小球是否撞地
				
				if (movable.currentVY > 0
						&& movable.currentY + movable.radius * 2 >= BallView.BOTTOM_LINE) {
					// 衰减水平方向的速度
					movable.currentVX = movable.currentVX
							* (1 - movable.impactFactor);
					// 衰减垂直方向的速度并改变方向
					movable.currentVY = 0 - movable.currentVY
							* (1 - movable.impactFactor);
					// 判断撞地衰减后的速度，太小就停止
					if (Math.abs(movable.currentVY) < BallView.DOWN_ZERO) {
						this.flag = false;
						
						DrawThread.moveBallNum--;
					} else {
						movable.startVY = movable.currentVY;
						movable.startY = movable.currentY;
						movable.startTime_Y = System.nanoTime();
					}
				}
				movable.startX = movable.currentX;
				movable.startTime_X = System.nanoTime();

			} else if ((movable.currentX + movable.radius / 2) >= BallView.WOOD_EDGE) {
				// 说明球已移出挡板
				movable.startTime_Y = System.nanoTime();
				movable.bFall = true;
			}
			try {
				Thread.sleep(sleepSpan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
