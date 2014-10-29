package com.kdcm.aidongdong.UI.Money;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	BallView ballView;
	SurfaceHolder surfaceHolder;
	boolean flag = false;
	int sleepSpan = 30;
	long start = System.nanoTime();
	int count = 0;
	public static int moveBallNum = 20;     //当前移动的球数，当所有球都不动时停止该进程
	
	public DrawThread(BallView ballView, SurfaceHolder surfaceHolder) {
		super();
		this.ballView = ballView;
		this.surfaceHolder = surfaceHolder;
		this.flag = true;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Canvas canvas = null;
		Log.e("num", moveBallNum+"");
		while(flag && moveBallNum>0){
			Log.i("kdcm", flag+"");
			try {
				canvas = surfaceHolder.lockCanvas(null);
				synchronized (surfaceHolder) {
					ballView.doDraw(canvas);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if(canvas!=null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			count++;
			if(count == 20){
				count = 0;
				long tempStamp = System.nanoTime();
				long span = tempStamp - start;
				start = tempStamp;
				double fps = Math.round(100000000000.0/span*20)/100.0;
				ballView.fps = "FPS:"+fps;
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
