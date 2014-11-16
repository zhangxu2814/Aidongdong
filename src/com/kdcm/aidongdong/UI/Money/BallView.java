package com.kdcm.aidongdong.UI.Money;

import java.util.ArrayList;
import java.util.Random;

import com.kdcm.aidongdong.R;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BallView extends SurfaceView implements SurfaceHolder.Callback {

	public static final int VX_MAX = 200; // 水平速度最大值
	public static final int VX_MIN = 15; // 水平速度最小值
	public static final int WOOD_EDGE = 70; // 木板有边沿X坐标
	public static int BOTTOM_LINE = 0; // 地面Y坐标，小球下落到此会弹起
	public static final int UP_ZERO = 20; // 上升过程中速度小于该值就算为0
	public static final int DOWN_ZERO = 50; // 撞击地面后速度小于该值就算为0

	Bitmap[] bitmaps = new Bitmap[6];
	Bitmap bpBack, bpWood;
	String fps = "";
	int ballNum = 20;
	int windowWidth;
	ArrayList<Movable> movables = new ArrayList<Movable>();
	public static DrawThread drawThread;

	public BallView(Context context, int windowWidth,int Height) {
		super(context);
		getHolder().addCallback(this);
		initBitmaps(getResources());
		this.windowWidth = windowWidth;
		this.BOTTOM_LINE=Height-100;
		initMovables();
		drawThread = new DrawThread(this, getHolder());
		Log.i("kdcm", drawThread + "");

	}

	private void initMovables() {
		// TODO Auto-generated method stub
		Random random = new Random();
		for (int i = 0; i < ballNum; i++) {
			int index = random.nextInt(3);
			Bitmap bitmap = null;
			if (i < ballNum / 2) {
				bitmap = bitmaps[3 + index % 3];
			} else {
				bitmap = bitmaps[index % 3];
			}
			Movable m = new Movable(50, 100 - bitmap.getHeight(),
					bitmap.getWidth() / 2, this.windowWidth, bitmap);
			movables.add(m);
		}
	}

	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(bpBack, 0, 0, null);
		canvas.drawBitmap(bpWood, 0, 0, null);
		for (Movable m : movables) {
			m.drawSelf(canvas);
		}

		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextSize(20);
		paint.setAntiAlias(true);
		canvas.drawText(fps, 350, 50, paint);
	}

	private void initBitmaps(Resources res) {
		// TODO Auto-generated method stub
		bitmaps[0] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);
		bitmaps[1] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);
		bitmaps[2] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);
		bitmaps[3] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);
		bitmaps[4] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);
		bitmaps[5] = BitmapFactory.decodeResource(res, R.drawable.icon_coin_2);

		bpBack = BitmapFactory.decodeResource(res, R.drawable.icon_bg_money);
		bpWood = BitmapFactory.decodeResource(res, R.drawable.icon_moneyred);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (!drawThread.isAlive()) {
			drawThread.start();
		} else {

		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		drawThread.flag = false;
		drawThread = null;
	}

}
